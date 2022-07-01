package at.stnwtr.asboet.roster

import io.javalin.core.security.BasicAuthCredentials
import java.time.Duration
import java.time.Instant

object RosterCache {
    private val CACHE_DURATION = Duration.ofHours(1)

    private val cache = mutableMapOf<Roster, Instant>()

    private fun removeUnused() {
        cache.entries
            .removeIf {
                it.value.plus(CACHE_DURATION).isBefore(Instant.now())
            }
    }

    fun loadRoster(username: String, password: String): Roster {
        val roster = cache.filterKeys { it.username == username && it.password == password }
            .entries
            .firstOrNull()?.key ?: Roster(username, password)

        cache[roster] = Instant.now()

        removeUnused()

        return roster
    }

    fun loadRoster(basicAuthCredentials: BasicAuthCredentials) =
        loadRoster(basicAuthCredentials.username, basicAuthCredentials.password)
}