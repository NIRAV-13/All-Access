package com.mobile.macs_13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.model.FeedbackModel


class UserFeedbackActivity : AppCompatActivity() {
    private lateinit var optiondropdown: Spinner
    private lateinit var meeting:String
    private lateinit var submit: Button
    private lateinit var advisor:String
    private lateinit var discussion: String
    private lateinit var duration:String
    private lateinit var subj:String
    private lateinit var suggestion:String
    private var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_feedback)

        supportActionBar?.title = "Feedback"
        optiondropdown=findViewById(R.id.spinner);
        var adapter = ArrayAdapter.createFromResource(this, R.array.options,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        optiondropdown.setAdapter(adapter);
        submit = findViewById(R.id.button4)

        submit.setOnClickListener {
            advisor = findViewById<TextView>(R.id.edt_advisor).text.toString()
            discussion = findViewById<TextView>(R.id.edt_discussion).text.toString()
            duration = findViewById<TextView>(R.id.edt_duration).text.toString()
            subj = findViewById<TextView>(R.id.edt_subj).text.toString()
            suggestion = findViewById<TextView>(R.id.edt_suggestion).text.toString()
            meeting  = optiondropdown.selectedItem.toString()
            saveData()
        }
    }
    private fun saveData(){
        val data = FeedbackModel(advisor, discussion, duration, subj, suggestion, meeting)
        db.collection("user_feedback").document().set(data).addOnSuccessListener {
            Toast.makeText(this, "Feedback Sent", Toast.LENGTH_LONG).show()
            val intent = Intent(this, StudentActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(this, "Error occurred",Toast.LENGTH_SHORT).show()
        }

    }
}