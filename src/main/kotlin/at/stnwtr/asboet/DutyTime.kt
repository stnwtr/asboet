package at.stnwtr.asboet

enum class DutyTime(val id: String) {
    DAY("Tag"),
    NIGHT("Nacht"),
    UNKNOWN("");

    companion object {
        fun of(id: String): DutyTime {
            return values().firstOrNull { it.id == id } ?: UNKNOWN
        }
    }
}