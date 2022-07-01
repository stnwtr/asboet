package at.stnwtr.asboet.access

import at.stnwtr.asboet.roster.RosterCache
import io.javalin.core.security.RouteRole
import io.javalin.http.Context
import io.javalin.http.ForbiddenResponse
import io.javalin.http.Handler

class AccessManager : io.javalin.core.security.AccessManager {
    override fun manage(handler: Handler, context: Context, routeRoles: MutableSet<RouteRole>) {
        if (!context.basicAuthCredentialsExist()) {
            throw ForbiddenResponse("Need basic auth")
        }

        val roster = RosterCache.loadRoster(context.basicAuthCredentials())

        if (!roster.tryLogin()) {
            throw ForbiddenResponse("Wrong credentials")
        }

        handler.handle(context)
    }
}