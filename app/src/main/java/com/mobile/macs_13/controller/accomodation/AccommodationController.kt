package com.mobile.macs_13.controller.accomodation

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.com.mobile.macs_13.model.*
import com.mobile.macs_13.com.mobile.macs_13.view.TermList
import com.mobile.macs_13.model.AdvisorAccomRequestModel
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton.firebadeDBInstance
// Advisor controller class for doing interaction with the Firebase having the Accommodation Collection

class AccommodationController {

    fun fetchAccomodations(selectedCourse: String, selectedProgram: String, selectedTerm: String, function: (Boolean) -> Unit){

        AccomodationList.clearList()

        firebadeDBInstance.collection("Accomodation")
            .whereEqualTo("course",selectedCourse)
            .whereEqualTo("term",selectedTerm)
            .whereEqualTo("program",selectedProgram)
            .get().addOnSuccessListener { documents->
            for (document in documents){

                var advisorAccomRequestModel = AdvisorAccomRequestModel(
                    uid = document["uid"] as String ,
                    name = document["name"] as String,
                    email = document["email"] as String,
                    phone = document["phone"] as String,
                    program = document["program"] as String,
                    course = document["course"] as String,
                    year = document["year"] as String,
                    term = document["term"] as String?,
                    docs = document["docs"] as String?,
                    imageLink = document["imageLink"] as String,
                    impact = document["impact"] as String,
                    consent = document["consent"] as String,
                    status =  document["status"] as String?,
                    timeStamp = (document["timeStamp"] as Timestamp).toDate(),
                    documentId = document.id
                )

                AccomodationList.addAccomodation(advisorAccomRequestModel)
            }
            function(true)

        }
            .addOnFailureListener{
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