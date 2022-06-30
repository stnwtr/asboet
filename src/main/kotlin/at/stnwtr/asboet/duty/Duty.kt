package at.stnwtr.asboet.duty

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class Duty(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") val date: LocalDate,
    val dutyType: DutyType,
    val dutyTime: DutyTime,
    val drivers: List<DutyWorker>,
    val firstParamedics: List<DutyWorker>,
    val secondParamedics: List<DutyWorker>
) {
    fun hasDuty(name: String): Boolean {
        return (drivers + firstParamedics + secondParamedics).map(DutyWorker::name).contains(name)
    }

    companion object {
        fun of(
            date: LocalDate,
            dutyType: DutyType,
            dutyTime: DutyTime,
            drivers: List<DutyWorker>,
            firstParamedic: List<DutyWorker>,
            secondParamedic: List<DutyWorker>
        ) = Duty(date, dutyType, dutyTime, drivers, firstParamedic, secondParamedic)
    }
}
