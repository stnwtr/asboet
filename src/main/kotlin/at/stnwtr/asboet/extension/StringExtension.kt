package at.stnwtr.asboet.extension

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun String.asDate(pattern: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, formatter)
}

fun String.asTime(pattern: String): LocalTime {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalTime.parse(this, formatter)
}

