package at.stnwtr.asboet

import at.stnwtr.asboet.extension.asDate
import at.stnwtr.asboet.roster.RosterCache
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.util.JavalinLogger
import io.javalin.http.BadRequestResponse
import io.javalin.http.ForbiddenResponse
import java.time.LocalDateTime

fun main() {
    val javalin = Javalin.create {
        it.enableCorsForAllOrigins()
    }

    javalin.routes {
        before("*") {
            JavalinLogger.info("${LocalDateTime.now()} -> ${it.ip()} -> ${it.fullUrl()}")
        }

        path("/api/v1") {
            get("/duty") {
                val username = it.queryParam("username")
                val password = it.queryParam("password")
                val date = it.queryParam("date")?.asDate() ?: throw BadRequestResponse()

                if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                    throw BadRequestResponse()
                }

                val roster = RosterCache.loadRoster(username, password)
                val duties = roster.dutiesForDay(date) ?: throw ForbiddenResponse()

                it.json(duties)
            }

            get("/personal") {
                val username = it.queryParam("username")
                val password = it.queryParam("password")

                if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                    throw BadRequestResponse()
                }

                val roster = RosterCache.loadRoster(username, password)
                val duties = roster.personalDuties() ?: throw ForbiddenResponse()

                it.json(duties)
            }

            get("/range") {
                val username = it.queryParam("username")
                val password = it.queryParam("password")
                val from = it.queryParam("from")?.asDate() ?: throw BadRequestResponse()
                val to = it.queryParam("to")?.asDate() ?: throw BadRequestResponse()

                if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                    throw BadRequestResponse()
                }

                val roster = RosterCache.loadRoster(username, password)
                val duties = roster.dutiesInRange(from, to) ?: throw ForbiddenResponse()

                it.json(duties)
            }
        }
    }

    javalin.start("0.0.0.0", 62461)
}