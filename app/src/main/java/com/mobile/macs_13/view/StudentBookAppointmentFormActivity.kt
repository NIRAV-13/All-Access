package com.mobile.macs_13.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.StudentActivity
import com.mobile.macs_13.controller.AdvisorController
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.model.AppointmentDetails
import com.mobile.macs_13.model.PushStudentNotification
import com.mobile.macs_13.model.SlotDetail
import com.mobile.macs_13.model.StudentNotificationData

// Activity class for student book appointment form.
class StudentBookAppointmentFormActivity : AppCompatActivity() {

    // On create event.
    override fun onCreate(savedInstanceState: Bundle?) {

        // Setting up view.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_book_appointment_form)

        // Fetching required details
        val appointmentDetails = this.intent.getSerializableExtra("appointmentDetails") as AppointmentDetails?
        val slotDetail = this.intent.getSerializableExtra("slotDetail") as SlotDetail?

        // Setting the fields for email, advisor name and time.
        var userEmail = findViewById<TextView>(R.id.studEmailAppointmentView)
        var selectedAdvisorName = findViewById<TextView>(R.id.selectedAdvisorView)
        var selectedTime = findViewById<TextView>(R.id.selectedTimeView)

        userEmail.text = appointmentDetails?.studentEmail.toString()
        selectedAdvisorName.text = appointmentDetails?.advisorName.toString()
        selectedTime.text = appointmentDetails?.appointmentStartTime.toString()

        val reason = findViewById<EditText>(R.id.reasonForAppointmentView).text

        // On submit button click listener.
        val submitButton = findViewById<Button>(R.id.studentAppointmentSubmitButton)
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

                                val advisorName = appointmentDetails?.advisorName
                                val startTime = appointmentDetails?.appointmentStartTime
                                val title = "Appointment confirmed."
                                val body = "You have an appointment with $advisorName on $startTime for the reason of: ${reason.toString()}"
                                val studentNotificationData = StudentNotificationData(title, body)

                                PushStudentNotification.pushStudentHomeNotification(studentNotificationData)
                                Toast.makeText(this,"Appointment has been confirmed.", Toast.LENGTH_LONG).show()
                                val handler = Handler()
                                handler.postDelayed(Runnable {
                                    val studentActivityIntent = Intent(this, StudentActivity::class.java)
                                    startActivity(studentActivityIntent)
                                },3000)



                            }
                            else{
                                Toast.makeText(this,"Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(this,"Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }

        // On cancel click listener.
        val cancelButton = findViewById<Button>(R.id.studentAppointmentCancelButton)
        cancelButton.setOnClickListener {
            val studentActivityIntent = Intent(this, StudentActivity::class.java)
            startActivity(studentActivityIntent)
        }


    }
}