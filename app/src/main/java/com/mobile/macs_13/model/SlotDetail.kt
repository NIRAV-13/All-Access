package com.mobile.macs_13.model

data class SlotDetail(
    var availabilityId :String,
    var startHour: String,
    var startMinute: String,
    var endHour: String,
    var endMinute: String){


    override fun toString(): String {
        return "$startHour : $startMinute ------ $endHour : $endMinute"
    }

}
