package at.stnwtr.asboet.duty

enum class DutyType(val id: String) {
    VEHICLE_340("62/340"),
    VEHICLE_341("62/341"),
    VEHICLE_342("62/342"),
    VEHICLE_343("62/343"),
    VEHICLE_344("62/344"),
    VEHICLE_345("62/345"),
    OFFICE("Büro"),
    JOURNAL("Journal"),
    OTHER("U/ZA/KR HA/ZDL"),
    UNKNOWN("");

    companion object {
        fun of(id: String): DutyType {
            return values().firstOrNull { it.id == id } ?: UNKNOWN
        }
    }
}