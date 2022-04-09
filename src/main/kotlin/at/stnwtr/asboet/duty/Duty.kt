package at.stnwtr.asboet.duty

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class Duty(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") val date: LocalDate,
    val dutyType: DutyType,
    val dutyTime: DutyTime,
    val drivers: Set<DutyWorker>,
    val firstParamedics: Set<DutyWorker>,
    val secondParamedics: Set<DutyWorker>
) {
    fun hasDuty(name: String): Boolean {
        return (drivers + firstParamedics + secondParamedics).map(DutyWorker::name).contains(name)
    }

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
