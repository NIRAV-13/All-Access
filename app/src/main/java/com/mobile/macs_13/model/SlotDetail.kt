package com.mobile.macs_13.model

import java.io.Serializable

data class SlotDetail (
    var availabilityId :String,
    var startHour: String,
    var startMinute: String,
    var endHour: String,
    var endMinute: String): Serializable{


    override fun toString(): String {
        return "$startHour : $startMinute ------ $endHour : $endMinute"
    }

}
