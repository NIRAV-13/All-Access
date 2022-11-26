package com.example.accomodationfeature

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobile.macs_13.R
import com.mobile.macs_13.StudentActivity
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAccomRequestModel


class StudentAccomodation : AppCompatActivity() {

    private lateinit var edtStudName: EditText
    private lateinit var edtStudEmail: TextView
    private lateinit var edtStudPrefName: EditText
    private lateinit var edtStudImpact: EditText
    private lateinit var edtStudConsent: EditText
    private lateinit var submitBtn: Button
    private lateinit var cancelBtn: Button
    private var studDB = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_accomodation)

        //to remove the action bar
        supportActionBar?.hide()


        edtStudEmail = findViewById(R.id.stud_email)
        edtStudName = findViewById(R.id.stud_name)
        edtStudPrefName= findViewById(R.id.edt_stud_pref_name)
        edtStudImpact = findViewById(R.id.impact_on_acad)
        edtStudConsent = findViewById(R.id.stud_consent)
        submitBtn = findViewById(R.id.accom_submit_btn)
        cancelBtn = findViewById(R.id.accom_cancel_btn)

        submitBtn.setOnClickListener {

            val studEmail = "alexjones@dal.ca"

            edtStudEmail.text = studEmail
            val studName = edtStudName.text.toString().trim()
            val studPrefName = edtStudPrefName.text.toString().trim()
            val studImpact = edtStudImpact.text.toString().trim()
            val studConsent = edtStudConsent.text.toString().trim()
            val status = "inProgress"

            // user profile database same data class object.
            // accomodocation form details.
//            getUserData(AdvisorAccomodationModel(studName,))
            val studRequest : StudentAccomRequestModel = StudentAccomRequestModel(User.getCurrentUserProfile().name,User.getCurrentUserProfile().email, User.getCurrentUserProfile().course, User.getCurrentUserProfile().program, User.getCurrentUserProfile().term, User.getCurrentUserProfile().year, User.getCurrentUserProfile().phone,User.getCurrentUserProfile().uid,studImpact,studConsent)
            }
            val studentMap = hashMapOf(
                "StudentName" to studName,
                "StudentEmail" to studEmail,
                "PreferredName" to studPrefName,
                "Impact" to studImpact,
                "Consent" to studConsent,
                "TimeStamp" to FieldValue.serverTimestamp(),
                "status" to status
            )

            val name = edtStudName.text.toString()
            val prefName = edtStudPrefName.text.toString()
            val impact = edtStudImpact.text.toString()
            val consent = edtStudConsent.text.toString()
            if (name.isBlank() || prefName.isBlank() || impact.isBlank() || consent.isBlank()) {
                Toast.makeText(this, "Please enter all the details!!", Toast.LENGTH_SHORT).show()
            } else {
                // getting current user id
//            val currentUserID= FirebaseAuth.getInstance().currentUser?.uid!!
                studDB.collection("Accomodation").document().set(studentMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Request submitted successfully", Toast.LENGTH_SHORT).show()
                        edtStudName.text.clear()
                        edtStudPrefName.text.clear()
                        edtStudImpact.text.clear()
                        edtStudConsent.text.clear()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        cancelBtn.setOnClickListener{

            val backIntent = Intent(this@StudentAccomodation, StudentActivity::class.java)
            startActivity(backIntent)
        }

    }
}