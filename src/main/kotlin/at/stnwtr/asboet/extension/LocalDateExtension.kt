package at.stnwtr.asboet.extension

import java.time.LocalDate
import java.time.temporal.IsoFields

fun LocalDate.weekOfYear(): Int {
    return get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
}

fun LocalDate.toDutyUrl() =
    "https://lv.svs-system.at/main.svs?act=&mod=dienstplan_4&act=show&dp=8&kw=${weekOfYear()}&j=${year}&ta=${dayOfWeek.value}&wt=${dayOfMonth}"
