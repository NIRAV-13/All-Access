package com.mobile.macs_13.controller.Instructor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mobile.macs_13.R

class InstructorCourseView : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_course_view)


        //both needed to be changed
        var coursename : TextView = findViewById(R.id.CourseName)
        var term : TextView = findViewById(R.id.term)




    }
}