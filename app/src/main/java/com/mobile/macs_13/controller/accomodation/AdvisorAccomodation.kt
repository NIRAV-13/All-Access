package com.mobile.macs_13.controller.accomodation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.utils.User

class AdvisorAccomodation : AppCompatActivity() {

    private lateinit var studName: TextView
    private lateinit var studEmail: TextView
    private lateinit var studUniv: TextView
    private lateinit var studProgram: TextView
    private lateinit var studCourse: TextView
    private lateinit var studTerm: TextView
    private lateinit var studYear: TextView
    private lateinit var studPhone: TextView
    private lateinit var studDocuments: TextView
    private lateinit var studReason: TextView
    private lateinit var advAccept: Button
    private lateinit var advReject: Button
    private lateinit var advComments: TextView
    private lateinit var accomDbRef : DatabaseReference
    private var accomDb = Firebase.firestore
    private lateinit var studDb : DatabaseReference
    private lateinit var mAuth: FirebaseAuth
//    val currentUserID = mAuth.currentUser

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor_accomodation)

        mAuth = FirebaseAuth.getInstance()
        studName = findViewById(R.id.accom_stud_name)
        studUniv = findViewById(R.id.univ_name)
        studProgram = findViewById(R.id.prog_name)
        studCourse = findViewById(R.id.course_name)
        studTerm = findViewById(R.id.stud_term)
        studEmail = findViewById(R.id.stud_email)
        studYear = findViewById(R.id.stud_year)
        studPhone = findViewById(R.id.stud_phone)
        studDocuments = findViewById(R.id.stud_doc)
        studReason = findViewById(R.id.stud_reason)
        advAccept = findViewById(R.id.adv_accept)
        advReject = findViewById(R.id.adv_reject)

        supportActionBar?.hide()
        // getting data
        val accomDbRef = accomDb.collection("Accomodation").document("zuiKPJimjaZEXQPmBgU9")
        accomDbRef.get().addOnSuccessListener{
            if(it!= null){
                val stud_Name = it.data?.get("StudentName")?.toString()
                val stud_Reason = it.data?.get("Impact")?.toString()
                studName.text = stud_Name
                studReason.text = stud_Reason
            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Data retrieval failed!! ", Toast.LENGTH_SHORT).show()
            }

        val studDbRef = accomDb.collection("User").document("zuiKPJimjaZEXQPmBgU9")
        studDbRef.get().addOnSuccessListener{
            if(it!= null){
                val stud_course = it.data?.get("course")?.toString()
                val stud_program = it.data?.get("program")?.toString()
                val stud_univ = it.data?.get("university")?.toString()
                val stud_phone = it.data?.get("phone")?.toString()
                val stud_email = it.data?.get("email")?.toString()
                val stud_year = it.data?.get("year")?.toString()
                val stud_term = it.data?.get("term")?.toString()
                studPhone.text = stud_phone
                studEmail.text = stud_email
                studUniv.text = stud_univ
                studTerm.text = stud_term
                studYear.text = stud_year
                studProgram.text = stud_program
                studCourse.text = stud_course

            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Data retrieval failed!! ", Toast.LENGTH_SHORT).show()
            }

        val advisorAcceptMap = hashMapOf(

            "status" to "Accepted",
            "TimeStamp" to FieldValue.serverTimestamp()
        )
        val advisorRejectMap = hashMapOf(

            "status" to "Rejected",
            "TimeStamp" to FieldValue.serverTimestamp()
        )


        advAccept.setOnClickListener{

            val dbRef = accomDb.collection("Accomodation").document("zuiKPJimjaZEXQPmBgU9")
            dbRef.get().addOnSuccessListener { documents ->
                if (documents?.data?.get("status") == "Accepted" || documents?.data?.get("status") == "Rejected") {
                    Toast.makeText(
                        this@AdvisorAccomodation,
                        "You have already made a decision on this request!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val accomDbRef =
                        accomDb.collection("Accomodation").document("zuiKPJimjaZEXQPmBgU9")
                    accomDbRef.update(advisorAcceptMap).addOnSuccessListener {

                        Toast.makeText(
                            this@AdvisorAccomodation,
                            "Student request accepted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                        .addOnFailureListener {
                            Toast.makeText(this@AdvisorAccomodation, "Error!", Toast.LENGTH_SHORT)
                                .show()
                        }

                }
            }
        }

        advReject.setOnClickListener{

            val dbRef = accomDb.collection("Accomodation").document("zuiKPJimjaZEXQPmBgU9")
            dbRef.get().addOnSuccessListener { documents ->
                if (documents?.data?.get("status") == "Accepted" || documents?.data?.get("status") == "Rejected") {
                    Toast.makeText(
                        this@AdvisorAccomodation,
                        "You have already made a decision on this request!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val accomDbRef =
                        accomDb.collection("Accomodation").document("zuiKPJimjaZEXQPmBgU9")
                    accomDbRef.update(advisorRejectMap).addOnSuccessListener {

                        Toast.makeText(
                            this@AdvisorAccomodation,
                            "Student request rejected!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                        .addOnFailureListener {
                            Toast.makeText(
                                this@AdvisorAccomodation,
                                "Error!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                }
            }
        }



    }
}