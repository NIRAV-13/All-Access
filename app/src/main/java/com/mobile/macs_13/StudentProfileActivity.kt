package com.mobile.macs_13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.mobile.macs_13.controller.utils.User

class StudentProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val intent = Intent(this, StudentActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}