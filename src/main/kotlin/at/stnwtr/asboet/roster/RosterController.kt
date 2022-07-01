package at.stnwtr.asboet.roster

import at.stnwtr.asboet.extension.asDate
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import org.slf4j.LoggerFactory

class RosterController {
    private val logger = LoggerFactory.getLogger(RosterController::class.java)

    fun dutiesForDay(context: Context) {
        val date = context.queryParam("date")?.asDate() ?: throw BadRequestResponse("Date formatting")

        val roster = RosterCache.loadRoster(context.basicAuthCredentials())

        logger.info("${context.basicAuthCredentials().username} requested duties for $date")

        context.future(roster.dutiesForDayAsync(date)) {
            context.json(it!!)
        }
    }

    fun dutiesInRange(context: Context) {
        val from = context.queryParam("from")?.asDate() ?: throw BadRequestResponse("Date formatting")
        val to = context.queryParam("to")?.asDate() ?: throw BadRequestResponse("Date formatting")

        if (to.isBefore(from)) {
            throw BadRequestResponse("To date > From date")
        }

        val roster = RosterCache.loadRoster(context.basicAuthCredentials())

        logger.info("${context.basicAuthCredentials().username} requested duties from $from to $to")

        context.future(roster.dutiesInRangeAsync(from, to)) {
            context.json(it!!)
        }
    }

    fun personalDuties(context: Context) {
        val roster = RosterCache.loadRoster(context.basicAuthCredentials())

        logger.info("${context.basicAuthCredentials().username} requested their personal duties")

        context.future(roster.personalDutiesAsync()) {
            context.json(it!!)
        }
    }
}