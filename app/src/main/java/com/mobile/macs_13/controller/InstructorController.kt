package com.mobile.macs_13.controller

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.model.AdvisorAccomRequestModel
import com.mobile.macs_13.model.StudentAccomRequestModel
import com.mobile.macs_13.model.StudentWithAccommodationList

class InstructorController {

//    fun getStudentWithAccommodation(sem: String, course: String, function: (Boolean) -> Unit){
//        var db= FirebaseFirestore.getInstance()
//
//        db.collection("Accomodation")
//            .get().addOnSuccessListener { documents ->
//                for(document in documents)
//                {
//                    var accomRequestModel = document.toObject(StudentAccomRequestModel::class.java)
//                    StudentWithAccommodationList.addStudentWithAccommodation(accomRequestModel)
//                }
//                function(true)
//            }
//            .addOnFailureListener{
//            }
//    }
//    fun getAccommodationDetails(uid: String,course: String, term: String,function: (StudentAccomRequestModel) -> Unit) {
//        var db= FirebaseFirestore.getInstance()
//        var accomRequestModel : StudentAccomRequestModel = StudentAccomRequestModel()
//        db.collection("Accomodation")
//            .whereEqualTo("uid",uid).whereEqualTo("Course",course).whereEqualTo("term",term)
//            .get().addOnSuccessListener { documents ->
//                for(document in documents){
//                    accomRequestModel = document.toObject(StudentAccomRequestModel::class.java)
////                    StudentWithAccommodationList.addStudentWithAccommodation(accomRequestModel)
//                    function(accomRequestModel)
//                }
//            }
//            .addOnFailureListener{ exception ->
//            }
////        return accomRequestModel
//    }


    fun getStudentWithAccommodation(function: (StudentAccomRequestModel) -> Unit){

//        db.collection("Accomodation")
//            .whereEqualTo("uid",uid).whereEqualTo("Course",course).whereEqualTo("term",term)
//            .get().addOnSuccessListener { documents ->
//                for(document in documents){
//                    accomRequestModel = document.toObject(StudentAccomRequestModel::class.java)
////                    StudentWithAccommodationList.addStudentWithAccommodation(accomRequestModel)
//                    function(accomRequestModel)
//                }
//            }
//            .addOnFailureListener{ exception ->
//            }
    }

    fun fetchCurrentStudents(term: String, course: String,function: (Boolean) -> Unit) {
        var db= FirebaseFirestore.getInstance()

        db.collection("Accomodation")
            .whereEqualTo("program",course).whereEqualTo("term",term)
            .get().addOnSuccessListener { documents ->
                for(document in documents){
                    val advisorAccomRequestModel = document.toObject(AdvisorAccomRequestModel::class.java)
                    StudentWithAccommodationList.addStudentWithAccommodation(advisorAccomRequestModel)
                }
                function(true)
            }
            .addOnFailureListener{ exception ->
                function(false)
            }

    }


}