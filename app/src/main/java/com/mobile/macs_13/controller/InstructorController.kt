package com.mobile.macs_13.controller

import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.model.StudentAccomRequestModel
import com.mobile.macs_13.model.StudentWithAccommodationList

class InstructorController {

    fun getStudentWithAccommodation(sem: String, course: String, function: (Boolean) -> Unit){
        var db= FirebaseFirestore.getInstance()

        db.collection("Accomodation")
            .get().addOnSuccessListener { documents ->
                for(document in documents)
                {
                    var accomRequestModel = document.toObject(StudentAccomRequestModel::class.java)
                    StudentWithAccommodationList.addStudentWithAccommodation(accomRequestModel)
                }
                function(true)
            }
            .addOnFailureListener{
            }
    }
    fun getAccommodationDetails(uid: String,function: (StudentAccomRequestModel) -> Unit) {
        var db= FirebaseFirestore.getInstance()
        var accomRequestModel : StudentAccomRequestModel = StudentAccomRequestModel()
        db.collection("Accomodation")
            .whereEqualTo("uid",uid)
            .get().addOnSuccessListener { documents ->
                for(document in documents){
                    accomRequestModel = document.toObject(StudentAccomRequestModel::class.java)
//                    StudentWithAccommodationList.addStudentWithAccommodation(accomRequestModel)
                    function(accomRequestModel)
                }
            }
            .addOnFailureListener{ exception ->
            }
//        return accomRequestModel
    }
}