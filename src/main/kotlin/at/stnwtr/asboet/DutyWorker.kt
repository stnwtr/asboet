package at.stnwtr.asboet

import java.time.LocalTime

data class DutyWorker(
    val name: String,
    val begin: LocalTime,
    val end: LocalTime
) {
    companion object {
        fun of(
            name: String,
            begin: LocalTime,
            end: LocalTime
        ) = DutyWorker(name, begin, end)

        val UNKNOWN = of(
            "UNKNOWN",
            LocalTime.MIN,
            LocalTime.MIN
        )
    }
}
