package com.mobile.macs_13.controller

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.*
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.model.Availability
import com.mobile.macs_13.model.StudentAccomRequestModel

class AdvisorController {

    var db = FirebaseFirestore.getInstance()

    fun changeAvailabilityToFalse(availabilityId: String?, function: (Boolean) -> Unit) {

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

    companion object {

        fun getRequestListFromDB(function: (ArrayList<StudentAccomRequestModel>) -> Unit) {

            var accomRequestList: ArrayList<StudentAccomRequestModel> =
                arrayListOf<StudentAccomRequestModel>()
            FirebaseRefSingleton.getFirebaseDBInstance().collection("Accomodation")
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .whereEqualTo("status", "inProgress")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if (error != null) {
                            Log.d(
                                "Error",
                                "Some Error in Connection to FireStore ${error.message.toString()}"
                            )
                            return
                        }

                        for (document: DocumentChange in value?.documentChanges!!) {

                            if (document.type == DocumentChange.Type.ADDED || document.type == DocumentChange.Type.MODIFIED /*|| document.type == DocumentChange.Type.REMOVED*/) {
                                accomRequestList.add(
                                    document.document.toObject(
                                        StudentAccomRequestModel::class.java
                                    )
                                )
                            }
                        }

                        Log.d("LIST", accomRequestList.size.toString())
                        function(accomRequestList)
                    }

                })

        }
    }
}