package com.mobile.macs_13.controller.accomodation

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.model.AdvisorAccomRequestModel

class AdvisorController {

    fun fetchAccomodations(function: (Boolean) -> Unit){
        var db = FirebaseFirestore.getInstance()

        db.collection("Accomodation").get().addOnSuccessListener { documents->
            for (document in documents){
                Log.d("data","${document.data}")
                val AdvisorAccomRequestModel = document.toObject(AdvisorAccomRequestModel::class.java)

                AccomodationList.addAccomodation(AdvisorAccomRequestModel)
            }
            function(true)

        }
            .addOnFailureListener{
                function(false)
            }

    }
}