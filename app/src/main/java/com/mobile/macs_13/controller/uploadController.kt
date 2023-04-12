package com.mobile.macs_13.com.mobile.macs_13.controller

import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.com.mobile.macs_13.model.uploadocument
import com.mobile.macs_13.com.mobile.macs_13.view.FileOperation.UploadFile

class uploadController {

    fun uploadDoc(studentid: String, Name: String, function: (Boolean) -> Unit) {
        var db = FirebaseFirestore.getInstance()
        db.collection("Student")
            .whereEqualTo("Student ID",studentid)
            .whereEqualTo("Name",Name)
            .get().addOnSuccessListener { docs ->
                for (document in docs) {
                    var Studentid = document.toObject(UploadFile::class.java)
                    Studentid.toString()

                }
                function(true)
            }
            .addOnFailureListener {
            }
    }
}
