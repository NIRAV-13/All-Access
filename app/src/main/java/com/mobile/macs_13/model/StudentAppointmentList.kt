package com.mobile.macs_13.model

object StudentAppointmentList {

    private var appointmentList = mutableListOf<AppointmentDetails>()

    fun addAppointment(appointmentDetails: AppointmentDetails){
        if(!appointmentList.contains(appointmentDetails))
            appointmentList.add(appointmentDetails)
    }

    fun getAppointments(): MutableList<AppointmentDetails> {
        return appointmentList;
    }

}