package com.mobile.macs_13.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.mobile.macs_13.model.*
import java.util.*


class StudentController {


    fun fetchAppointments(studentEmail: String, function: (Boolean) -> Unit){

        var db = FirebaseFirestore.getInstance()
        db.collection("AppointmentDetails")
            .whereEqualTo("studentEmail", studentEmail)
            .get()
            .addOnSuccessListener {
                    documents ->

                for (document in documents){

//                    val advisorEmail = document.data["advisorEmail"] as String
//                    val startTime = (document.data["startTime"] as Timestamp).toDate()
//                    val endTime = (document.data["endTime"] as Timestamp).toDate()
//                    val studentEmail = document.data["studentEmail"] as String
//                    val status = document.data["status"] as Boolean
                    val appointmentDetails = document.toObject(AppointmentDetails::class.java)

                    StudentAppointmentList.addAppointment(appointmentDetails)

                }
                function(true)
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents: ", exception)
                function(false)
            }

    }

    fun bookAppointment(appointmentDetails: AppointmentDetails?, function: (Boolean) -> Unit){

        var db = FirebaseFirestore.getInstance()

        db.collection("AppointmentDetails")
            .add(appointmentDetails!!)
            .addOnSuccessListener {
                function(true)
            }

            .addOnFailureListener { exception ->
                function(false)
            }

    }

    fun fetchAvailability( advisorEmail: String , startTime: Long, midNightEndTime: Long, function: (Boolean) -> Unit){

        AvailableAppointmentList.getAvailability().clear()
        var db = FirebaseFirestore.getInstance()

        db.collection("Availability")
            .whereEqualTo("advisorEmail", advisorEmail)
            .whereEqualTo("isAvailable",true)
            .whereGreaterThanOrEqualTo("startTime", Date(startTime))
            .whereLessThanOrEqualTo("startTime", Date(midNightEndTime))
            .get()
            .addOnSuccessListener {
                    documents ->

                for (document in documents){

                    val advisorEmail = document.data["advisorEmail"] as String
                    val isAvailable = document.data["isAvailable"] as Boolean
                    val startTime = document.data["startTime"] as Timestamp
                    val endTime = document.data["endTime"] as Timestamp
                        val availability = Availability( document.id,
                            advisorEmail, isAvailable, startTime, endTime)

                        AvailableAppointmentList.addAvailability(availability)

                }
                function(true)
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents: ", exception)
                function(false)
            }


    }

    fun fetchAdvisorList(function: (Boolean) -> Unit) {


        var db = FirebaseFirestore.getInstance()
        db.collection("Advisor")
            .get()
            .addOnSuccessListener {
                    documents ->

                for (document in documents){
                    Log.d("data","$document")
                    val advisor = document.toObject(UserProfile::class.java)
                    AdvisorList.addAdvisor(advisor)
                }
                function(true)
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents: ", exception)
                function(false)
            }

    }

}