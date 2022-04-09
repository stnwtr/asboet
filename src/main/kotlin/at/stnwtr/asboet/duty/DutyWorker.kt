package at.stnwtr.asboet.duty

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalTime

data class DutyWorker(
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") val begin: LocalTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") val end: LocalTime,
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
