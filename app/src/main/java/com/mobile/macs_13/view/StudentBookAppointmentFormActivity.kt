package com.mobile.macs_13.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.mobile.macs_13.R
import com.mobile.macs_13.StudentActivity
import com.mobile.macs_13.controller.AdvisorController
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.model.AppointmentDetails
import com.mobile.macs_13.model.SlotDetail


class StudentBookAppointmentFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_book_appointment_form)

        val appointmentDetails = this.intent.getSerializableExtra("appointmentDetails") as AppointmentDetails?

        val slotDetail = this.intent.getSerializableExtra("slotDetail") as SlotDetail?

        val userEmail = findViewById<TextView>(R.id.studEmailAppointmentView)

        val selectedAdvisorName = findViewById<TextView>(R.id.selectedAdvisorView)

        val selectedTime = findViewById<TextView>(R.id.selectedTimeView)

        userEmail.text = appointmentDetails?.studentEmail.toString()

        selectedAdvisorName.text = appointmentDetails?.advisorName.toString()

        selectedTime.text = appointmentDetails?.appointmentStartTime.toString()

        val reason = findViewById<EditText>(R.id.reasonForAppointmentView).text

        val submitButton = findViewById<Button>(R.id.studentAppointmentSubmitButton)
        val cancelButton = findViewById<Button>(R.id.studentAppointmentCancelButton)

        submitButton.setOnClickListener {

            if(reason.isBlank()) {
                Toast.makeText(this,"Please provide reason for appointment.", Toast.LENGTH_SHORT).show()
            }
            else{

                appointmentDetails?.appointmentReason = reason.toString()
                val studentController = StudentController()
                val advisorController = AdvisorController()

                studentController.bookAppointment(
                    appointmentDetails){ bookStatus ->

                    if(bookStatus){

                        advisorController.changeAvailabilityToFalse(slotDetail?.availabilityId){ changeAvailableStatus ->

                            if(changeAvailableStatus){

                                val studentActivityIntent = Intent(this, StudentActivity::class.java)
                                startActivity(studentActivityIntent)

                            }
                            else{
                                Toast.makeText(this,"Appointment availability issue. Check with dal support.", Toast.LENGTH_SHORT)
                            }
                        }
                    }
                    else{
                        Toast.makeText(this,"Appointment book issue. Check with dal support.", Toast.LENGTH_SHORT)
                    }

                }

            }
        }



        cancelButton.setOnClickListener {
            val studentActivityIntent = Intent(this, StudentActivity::class.java)
            startActivity(studentActivityIntent)
        }



}
}