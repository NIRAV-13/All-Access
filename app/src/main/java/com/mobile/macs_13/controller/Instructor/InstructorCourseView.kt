package com.mobile.macs_13.controller.Instructor

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mobile.macs_13.R

class InstructorCourseView : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var  studentNav: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_course_view)

        val sem = this.intent.getStringArrayExtra("selectedSem")

        val course = this.intent.getStringArrayExtra("selectedCourse")



        //both needed to be changed
        var coursename : TextView = findViewById(R.id.CourseName)
        var term : TextView = findViewById(R.id.term)

        studentNav = findViewById(R.id.StudentA)
        studentNav.setOnClickListener {
            val searchDetailsIntent = Intent(this, InstructorStudentAccomodation::class.java)
            startActivity(searchDetailsIntent)
        }

    }
}