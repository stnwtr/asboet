package at.stnwtr.asboet

import at.stnwtr.asboet.access.AccessManager
import at.stnwtr.asboet.access.Role
import at.stnwtr.asboet.roster.RosterController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun main() {
    val javalin = Javalin.create {
        it.enableCorsForAllOrigins()
        it.accessManager(AccessManager())
    }

    val controller = RosterController()

    javalin.routes {
        path("/api") {
            path("/v1") {
                get("/duties", controller::dutiesForDay, Role.AUTHENTICATED)
                get("/range", controller::dutiesInRange, Role.AUTHENTICATED)
                get("/personal", controller::personalDuties, Role.AUTHENTICATED)
            }
        }
    }

    javalin.start("0.0.0.0", 62461)
}