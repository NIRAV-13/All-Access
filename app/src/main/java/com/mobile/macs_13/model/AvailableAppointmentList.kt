package com.mobile.macs_13.model

object AvailableAppointmentList {

    private var availableAppointmentList = mutableListOf<Availability>()

    fun addAvailability(availability: Availability){
        if(!availableAppointmentList.contains(availability))
            availableAppointmentList.add(availability)
    }

    fun getAvailability(): MutableList<Availability> {
        return availableAppointmentList;
    }

}