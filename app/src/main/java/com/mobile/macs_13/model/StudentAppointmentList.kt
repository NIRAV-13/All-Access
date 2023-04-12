package com.mobile.macs_13.model

// Singleton class for storing appointments of students.
object StudentAppointmentList {

    // Mutable list of appointments.
    private var appointmentList = mutableListOf<AppointmentDetails>()

    // Adding appointment details to list.
    fun addAppointment(appointmentDetails: AppointmentDetails){
        if(!appointmentList.contains(appointmentDetails)){
            appointmentList.add(appointmentDetails)
        }
    }

    // Fetching all available appointments.
    fun getAppointments(): MutableList<AppointmentDetails> {
        return appointmentList;
    }

    // C
    fun clearList(){
        appointmentList.clear()
    }

}