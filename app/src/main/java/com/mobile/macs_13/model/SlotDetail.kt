package com.mobile.macs_13.model

import java.io.Serializable

// Data class to store details about a slot.
data class SlotDetail(
    var availabilityId: String? = null,
    var startHour: String? = null,
    var startMinute: String? = null,
    var endHour: String?= null,
    var endMinute: String): Serializable{

    override fun toString(): String {
        return "$startHour : $startMinute ----- $endHour : $endMinute"
    }

}
