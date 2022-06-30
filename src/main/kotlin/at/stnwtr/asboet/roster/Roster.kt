package at.stnwtr.asboet.roster

import at.stnwtr.asboet.duty.Duty
import at.stnwtr.asboet.duty.DutyTime
import at.stnwtr.asboet.duty.DutyType
import at.stnwtr.asboet.duty.DutyWorker
import at.stnwtr.asboet.extension.asDate
import at.stnwtr.asboet.extension.asTime
import at.stnwtr.asboet.extension.toDutyUrl
import io.javalin.core.util.Header
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.net.CookieManager
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.LocalDate
import kotlin.streams.asSequence

class Roster(
    val username: String,
    val password: String
) {
    private val client = HttpClient.newBuilder()
        .cookieHandler(CookieManager())
        .build()

    private var name: String = "UNKNOWN"
        get() {
            if (field != "UNKNOWN") {
                return field
            }

            val request = HttpRequest.newBuilder()
                .uri(URI.create("https://lv.svs-system.at/main.svs?do="))
                .GET()
                .build()

            val responseBody = client.send(request, BodyHandlers.ofString()).body()

            val soup = Jsoup.parse(responseBody)
                .select("#leftColumnx center img")

            if (field == "UNKNOWN" && soup.isNotEmpty()) {
                field = soup.attr("title").trim()
            }

            return field
        }

    private fun login() {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://lv.svs-system.at/index.svs?do=login"))
            .POST(HttpRequest.BodyPublishers.ofString("username=$username&password=$password"))
            .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
            .build()

        client.send(request, BodyHandlers.discarding())
    }

    private fun logout() {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://lv.svs-system.at/main.svs?system=logout"))
            .GET()
            .build()

        client.send(request, BodyHandlers.discarding())
    }

    private fun loggedIn(): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://lv.svs-system.at/main.svs?do="))
            .GET()
            .build()

        val responseBody = client.send(request, BodyHandlers.ofString()).body()

        return responseBody.contains("Logout")
    }

    private fun tryLogIn(): Boolean {
        if (!loggedIn()) {
            login()
        }

        return loggedIn()
    }

    fun dutiesForDay(date: LocalDate): Set<Duty>? {
        if (!tryLogIn()) {
            return null
        }

        val request = HttpRequest.newBuilder()
            .uri(URI.create(date.toDutyUrl()))
            .GET()
            .build()

        val responseBody = client.send(request, BodyHandlers.ofString()).body()
        val soup = Jsoup.parse(responseBody)
        val tables = soup.select("table")

        val dayTable = tables[0]
        val dayTrs = dayTable.select("tr")
        val dayDuties = dayTrs.asSequence().drop(1).map { rowToDuty(it, date, DutyTime.DAY) }.toSet()

        val nightTable = tables[1]
        val nightTrs = nightTable.select("tr")
        val nightDuties = nightTrs.asSequence().drop(1).map { rowToDuty(it, date, DutyTime.NIGHT) }.toSet()

        return dayDuties + nightDuties
    }

    fun dutiesInRange(from: LocalDate, to: LocalDate): Set<Duty>? {
        if (!tryLogIn()) {
            return null
        }

        return from.datesUntil(to.plusDays(1)).asSequence().map { dutiesForDay(it)!! }
            .flatten()
            .toSet()
    }

    private fun personalDutyDates(): Set<LocalDate>? {
        if (!tryLogIn()) {
            return null
        }

        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://lv.svs-system.at/main.svs?do=&mod=dienstplan_4&dp=8"))
            .GET()
            .build()

        val responseBody = client.send(request, BodyHandlers.ofString()).body()
        val soup = Jsoup.parse(responseBody)
        val table = soup.select("table")[1]
        val trs = table.select("tr")

        return trs.asSequence().drop(1)
            .map { it.select("td").first()?.text()?.asDate("dd.MM.yyyy") }
            .filterNotNull()
            .toSet()
    }

    fun personalDuties(): Set<Duty>? {
        if (!tryLogIn()) {
            return null
        }

        return personalDutyDates()!!
            .asSequence()
            .flatMap { dutiesForDay(it)!! }
            .filter { it.hasDuty(name) }
            .toSet()
    }

    // Helper methods for parsing ROWs
    private fun rowToDuty(row: Element, date: LocalDate, time: DutyTime) = Duty(
        date,
        DutyType.of(row.child(1).html().split("<br>")[0]),
        time,
        parseWorkerRow(row.child(2).html()),
        parseWorkerRow(row.child(3).html()),
        parseWorkerRow(row.child(4).html()),
    )

    private fun parseWorkerRow(row: String): List<DutyWorker> {
        return row.split("<br>")
            .asSequence().map { Jsoup.parse(it).text() }
            .chunked(2)
            .filter { it[1].contains("Uhr") }
            .map { parseWorker(it[0], it[1]) }
            .toList()
    }

    private fun parseWorker(name: String, time: String): DutyWorker {
        val timeSpan = time.split(" ")[0].split("-")
        return DutyWorker(name, timeSpan[0].asTime(), timeSpan[1].asTime())
    }
}
