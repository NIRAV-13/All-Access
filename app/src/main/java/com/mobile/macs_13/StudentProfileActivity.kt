package com.mobile.macs_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class StudentProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        val studentImage = findViewById<ImageView>(R.id.student_profile_image)
        val studentName = findViewById<TextView>(R.id.student_profile_name)
        val studentEmail = findViewById<TextView>(R.id.student_profile_email)
        val studentPhone = findViewById<TextView>(R.id.student_profile_phone)
        val studentProgram = findViewById<TextView>(R.id.student_profile_program)

        Glide.with(this.baseContext)
            .load("https://firebasestorage.googleapis.com/v0/b/chatapp-11645.appspot.com/o/IMG_20200303_000056.jpg?alt=media&token=1d65b825-cfd9-4d80-b910-8e2080000cda")
            .centerCrop().placeholder(R.drawable.ic_profile)
            .into(studentImage);

        studentName.text = "NAME - " + "Ankush Mudgal"
        studentEmail.text = "EMAIL - " + "test@test.ca"
        studentPhone.text = "PHONE - " + "67578329189"
        studentProgram.text = "PROGRAM - " + "MACS"
    }
}