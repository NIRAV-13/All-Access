package com.mobile.macs_13.controller

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.model.Advisor
import com.mobile.macs_13.model.AdvisorList

class StudentController {

    fun bookAppointment(){

    }

    fun fetchAdvisorList(function: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Advisor")
            .get()
            .addOnSuccessListener {
                    documents ->

                for (document in documents){

                    val advisorEmail = document.data["advisorEmail"] as String
                    val advisorCampus = document.data["advisorCampus"] as String
                    val advisorName = document.data["advisorName"] as String
                    val advisorPassword = document.data["advisorPassword"] as String
                    val advisorUniversityName = document.data["advisorUniversityName"] as String

                    val advisor = Advisor(
                        advisorEmail, advisorName, advisorPassword, advisorCampus, advisorUniversityName)

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