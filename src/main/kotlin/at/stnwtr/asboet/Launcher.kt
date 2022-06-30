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
            get("/duty") { context ->
                val username = context.queryParam("username")
                val password = context.queryParam("password")
                val date = context.queryParam("date")?.asDate() ?: throw BadRequestResponse()

                if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                    throw BadRequestResponse()
                }

                val roster = RosterCache.loadRoster(username, password)

                context.future(roster.dutiesForDayAsync(date)) {
                    context.json(it ?: throw ForbiddenResponse())
                }
            }

            get("/personal") { context ->
                val username = context.queryParam("username")
                val password = context.queryParam("password")

                if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                    throw BadRequestResponse()
                }

                val roster = RosterCache.loadRoster(username, password)

                context.future(roster.personalDutiesAsync()) {
                    context.json(it ?: throw ForbiddenResponse())
                }
            }

            get("/range") { context ->
                val username = context.queryParam("username")
                val password = context.queryParam("password")
                val from = context.queryParam("from")?.asDate() ?: throw BadRequestResponse()
                val to = context.queryParam("to")?.asDate() ?: throw BadRequestResponse()

                if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                    throw BadRequestResponse()
                }

                val roster = RosterCache.loadRoster(username, password)

                context.future(roster.dutiesInRangeAsync(from, to)) {
                    context.json(it ?: throw ForbiddenResponse())
                }
            }
        }
    }

    javalin.start("0.0.0.0", 62461)
}