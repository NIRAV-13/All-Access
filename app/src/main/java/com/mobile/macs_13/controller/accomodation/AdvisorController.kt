package com.mobile.macs_13.controller.accomodation

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.com.mobile.macs_13.model.*
import com.mobile.macs_13.com.mobile.macs_13.view.TermList
import com.mobile.macs_13.model.AdvisorAccomRequestModel

class AdvisorController {

    fun fetchAccomodations(selectedCourse: String, selectedProgram: String, selectedTerm: String, function: (Boolean) -> Unit){
        var db = FirebaseFirestore.getInstance()

        AccomodationList.clearList()

        db.collection("Accomodation")
            .whereEqualTo("course",selectedCourse)
            .whereEqualTo("term",selectedTerm)
            .whereEqualTo("program",selectedProgram)
            .get().addOnSuccessListener { documents->
            for (document in documents){

                var advisorAccomRequestModel = AdvisorAccomRequestModel(
                    uid = document["uid"] as String ,
                    studentName = document["name"] as String,
                    studentEmail = document["email"] as String,
                    studentPhone = document["phone"] as String,
                    studentProgram = document["program"] as String,
                    studentCourse = document["course"] as String,
                    studentYear = document["year"] as String,
                    studentTerm = document["term"] as String?,
                    studentDocs = document["docs"] as String?,
                    studentImageLink = document["imageLink"] as String,
                    studentImpact = document["impact"] as String,
                    studentConsent = document["consent"] as String,
                    studentStatus =  document["status"] as String?,
                    studentTimeStamp = (document["timeStamp"] as Timestamp).toDate(),
                    documentId = document.id
                )
//                val AdvisorAccomRequestModel = document.toObject(AdvisorAccomRequestModel::class.java)
                Log.d("XYZ","$advisorAccomRequestModel")
                AccomodationList.addAccomodation(advisorAccomRequestModel)
            }
            function(true)

        }
            .addOnFailureListener{
                Log.d("XYZ","error")

                function(false)
            }

    }

    fun fetchProgramsForDropDown(function: (Boolean) -> Unit){
        var db = FirebaseFirestore.getInstance()

        db.collection("Program").get().addOnSuccessListener { documents->
            for (document in documents){
                val programModel = document.toObject(Program::class.java)
                ProgramList.addProgram(programModel)
            }
            function(true)

        }
        .addOnFailureListener{
                function(false)
        }

    }
    fun fetchCourseForDropDown(function: (Boolean) -> Unit){
        var db = FirebaseFirestore.getInstance()

        db.collection("Course").get().addOnSuccessListener { documents->
            for (document in documents){
                Log.d("Data","${document.data}")
                val courseModel = document.toObject(Course::class.java)
                CourseList.addCourse(courseModel)
            }
            function(true)

        }
            .addOnFailureListener{
                function(false)
            }

    }
    fun fetchTermForDropDown(function: (Boolean) -> Unit){
        var db = FirebaseFirestore.getInstance()

        db.collection("Term").get().addOnSuccessListener { documents->
            for (document in documents){
                val termModel  = document.toObject(Term::class.java)
                TermList.addTerm(termModel)
            }
            function(true)

        }
            .addOnFailureListener{
                function(false)
            }
    }

}