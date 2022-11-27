package com.mobile.macs_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mobile.macs_13.controller.utils.User

class InstructorProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_profile)

        val advisorImage = findViewById<ImageView>(R.id.instructor_profile_image)
        val advisorName = findViewById<TextView>(R.id.instructor_profile_name)
        val advisorEmail = findViewById<TextView>(R.id.instructor_profile_email)
        val advisorPhone = findViewById<TextView>(R.id.instructor_profile_phone)
        val advisorInfo = findViewById<TextView>(R.id.instructor_profile_text)

        Glide.with(this.baseContext)
            .load(User.getCurrentUserProfile().imageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(advisorImage);

        advisorName.text = "NAME - " + User.getCurrentUserProfile().name
        advisorEmail.text = "EMAIL - " + User.getCurrentUserProfile().email
        advisorPhone.text = "PHONE - " + User.getCurrentUserProfile().phone
        advisorInfo.text = "INFO - " + User.getCurrentUserProfile().course
        Log.d("INS", User.getCurrentUserProfile().toString())

    }
}