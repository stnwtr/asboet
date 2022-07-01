package at.stnwtr.asboet.duty

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalTime

data class DutyWorker(
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") val begin: LocalTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") val end: LocalTime,
)
