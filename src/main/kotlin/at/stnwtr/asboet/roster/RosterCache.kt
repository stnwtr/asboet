package at.stnwtr.asboet.roster

import java.time.Duration
import java.time.Instant

object RosterCache {
    private val cache = mutableMapOf<Roster, Instant>()

    private fun removeUnused() {
        cache.entries
            .removeIf { it.value.plus(Duration.ofHours(1)).isBefore(Instant.now()) }
    }

    fun loadRoster(username: String, password: String): Roster {
        removeUnused()

        val roster = cache.filterKeys { it.username == username && it.password == password }
            .entries
            .firstOrNull()?.key ?: Roster(username, password)

        cache[roster] = Instant.now()

        return roster
    }
}