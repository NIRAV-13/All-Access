package com.example.accomodationfeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.accomodation.AdvisorAccomodation

class AdvisorAccomodationHome : AppCompatActivity() {

    private lateinit var studReqBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor_accomodation_home)

        //to remove the action bar
        supportActionBar?.hide()

        studReqBtn.setOnClickListener{

            val email = "alexjones@dal.ca"

            var db = FirebaseFirestore.getInstance()

            db.collection("")

            // navigate to the advisor requests page
            val accomIntent = Intent(this@AdvisorAccomodationHome, AdvisorAccomodation::class.java )
            finish()
            startActivity(accomIntent)
        }
    }
}