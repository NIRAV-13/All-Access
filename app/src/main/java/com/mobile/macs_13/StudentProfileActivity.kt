package com.mobile.macs_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mobile.macs_13.controller.utils.User

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
            .load(User.getCurrentUserProfile().imageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(studentImage);

        studentName.text = "NAME - " + User.getCurrentUserProfile().name
        studentEmail.text = "EMAIL - " + User.getCurrentUserProfile().email
        studentPhone.text = "PHONE - " + User.getCurrentUserProfile().phone
        studentProgram.text = "PROGRAM - " + User.getCurrentUserProfile().program
    }
}