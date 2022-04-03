package at.stnwtr.asboet

import java.time.LocalDate

data class Duty(
    val date: LocalDate,
    val dutyType: DutyType,
    val dutyTime: DutyTime,
    val drivers: Set<DutyWorker>,
    val firstParamedic: Set<DutyWorker>,
    val secondParamedic: Set<DutyWorker>
) {
    companion object {
        fun of(
            date: LocalDate,
            dutyType: DutyType,
            dutyTime: DutyTime,
            drivers: Set<DutyWorker>,
            firstParamedic: Set<DutyWorker>,
            secondParamedic: Set<DutyWorker>
        ) = Duty(date, dutyType, dutyTime, drivers, firstParamedic, secondParamedic)

        val UNKNOWN = of(
            LocalDate.MIN,
            DutyType.UNKNOWN,
            DutyTime.UNKNOWN,
            emptySet(),
            emptySet(),
            emptySet()
        )
    }
}
