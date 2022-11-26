package com.mobile.macs_13.controller

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.model.Availability

class AdvisorController {

    var db = FirebaseFirestore.getInstance()

    fun changeAvailabilityToFalse(availabilityId: String?, function: (Boolean) -> Unit){

        db.collection("Availability")
            .document(availabilityId.toString())
            .update("isAvailable", false)
            .addOnSuccessListener {
                function(true)
            }
            .addOnFailureListener { exception ->
                function(false)
            }
    }
}