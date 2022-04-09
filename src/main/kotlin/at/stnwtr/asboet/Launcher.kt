package at.stnwtr.asboet

import at.stnwtr.asboet.extension.asDate
import at.stnwtr.asboet.roster.Roster
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

        path("/v1") {
            get("/duty") {
                val username = it.queryParam("username")
                val password = it.queryParam("password")
                val date = it.queryParam("date")

                if (username.isNullOrEmpty() || password.isNullOrEmpty() || date.isNullOrEmpty()) {
                    throw BadRequestResponse()
                }

                val roster = Roster(username, password)

                val duties = roster.dutiesForDay(date.asDate("yyyy-MM-dd")) ?: throw ForbiddenResponse()

                it.json(duties)
            }

//            get("/personal") {
//
//            }
//
//            get("/range") {
//
//            }
        }
    }

    javalin.start("localhost", 62461)
}