package com.mobile.macs_13.controller.Instructor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.mobile.macs_13.R
import com.mobile.macs_13.model.StudentAccomRequestModel
import org.w3c.dom.Text


class InstructorStudentAccomodation : AppCompatActivity()
{

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_student_accomodation)
        val studentDetails = this.intent.getSerializableExtra("studentDetails") as StudentAccomRequestModel?
        Log.d("XYZ","$studentDetails")


        var emailid = findViewById<TextView>(R.id.Student_email)
        emailid.setText("Student Email: "+studentDetails?.email)

        var year = findViewById<TextView>(R.id.Year)
        year.setText("Student Enrollment Year: "+studentDetails?.year)

        var studentName = findViewById<TextView>(R.id.StudentName)
        studentName.setText("Student Name: "+studentDetails?.name)

        var consentSigned = findViewById<TextView>(R.id.Accommodationtype)
        consentSigned.setText("Consent Signed: "+studentDetails?.consent)

        var AccommodationStatus = findViewById<TextView>(R.id.status)
        AccommodationStatus.setText("Student Accommodation Status: "+studentDetails?.status)

        var studentPhoneNumber = findViewById<TextView>(R.id.phone_number)
        studentPhoneNumber.setText("Student Phone Number: "+studentDetails?.phone)









    }
}