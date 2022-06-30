package at.stnwtr.asboet.extension

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.asDate(pattern: String = "yyyy-MM-dd"): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return try {
        LocalDate.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        null
    }
}

fun String.asTime(pattern: String = "HH:mm"): LocalTime {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalTime.parse(this, formatter)
}

