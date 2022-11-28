package com.mobile.macs_13.model

// Singleton class for available appointments.
object AvailableAppointmentList {

    // Mutable list of available appointments.
    private var availableAppointmentList = mutableListOf<Availability>()

    // Add availability to list.
    fun addAvailability(availability: Availability){
        if(!availableAppointmentList.contains(availability))
            availableAppointmentList.add(availability)
    }

    // Fetching all availability list.
    fun getAvailability(): MutableList<Availability> {
        return availableAppointmentList;
    }

}