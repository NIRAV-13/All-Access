package com.mobile.macs_13.controller

import android.util.Log
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.model.*
import java.util.*

// Student Controller class to handle operations for students.
class StudentController {

    // Firestore database.
    private var fireStoreDB = FirebaseRefSingleton.getFirebaseDB()

    // Fetching appointments for the student.
    fun fetchAppointments(studentEmail: String, function: (Boolean) -> Unit){
        StudentAppointmentList.clearList()
        fireStoreDB.collection("AppointmentDetails")
            .whereEqualTo("studentEmail", studentEmail)
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents){
                    val appointmentDetails = document.toObject(AppointmentDetails::class.java)
                    StudentAppointmentList.addAppointment(appointmentDetails)
                }

                function(true)
            }
            .addOnFailureListener { exception ->
                Log.w("FirebaseException", "There was error in database", exception)
                function(false)
            }

    }

    // Booking an appointment with advisor.
    fun bookAppointment(appointmentDetails: AppointmentDetails?, function: (Boolean) -> Unit){

        fireStoreDB.collection("AppointmentDetails")
            .add(appointmentDetails!!)
            .addOnSuccessListener {
                function(true)
            }

            .addOnFailureListener { exception ->
                Log.w("FirebaseException", "There was error in database", exception)
                function(false)
            }

    }

    // Fetching availability of an selected advisor for selected date.
    fun fetchAvailability( advisorEmail: String , startTime: Long, midNightEndTime: Long, function: (Boolean) -> Unit){

        AvailableAppointmentList.getAvailability().clear()
        Log.d("XYZ","$midNightEndTime")
        fireStoreDB.collection("Availability")
            .whereEqualTo("advisorEmail", advisorEmail)
            .whereEqualTo("isAvailable",true)
            .whereGreaterThanOrEqualTo("startTime", Date(startTime))
            .whereLessThanOrEqualTo("startTime", Date(midNightEndTime))
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents){
                    val availability = document.toObject(Availability::class.java)
                    AvailableAppointmentList.addAvailability(availability)
                }

                function(true)
            }
            .addOnFailureListener { exception ->
                Log.w("FirebaseException", "There was error in database", exception)
                function(false)
            }


    }

    // Fetching list of available advisors.
    fun fetchAdvisorList(function: (Boolean) -> Unit) {
        AdvisorList.clearList()
        fireStoreDB.collection("Advisor")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents){
                    val advisor = document.toObject(UserProfile::class.java)
                    AdvisorList.addAdvisor(advisor)
                }

                function(true)
            }
            .addOnFailureListener { exception ->
                Log.w("FirebaseException", "There was error in database", exception)
                function(false)
            }

    }

}