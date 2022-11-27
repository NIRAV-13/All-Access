package com.mobile.macs_13.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAppointmentListAdapter

// Activity class for students to book appointment with advisor.
class StudentBookAppointmentHome : AppCompatActivity() {

    // Declaring required private attributes.
    private lateinit var recyclerView: RecyclerView
    private lateinit var addAppointmentButton: FloatingActionButton
    private var studentController: StudentController = StudentController()
    private lateinit var studentAppointmentListAdapter: StudentAppointmentListAdapter
    private val student = User.getCurrentUserProfile()

    // Activity onCreate methods
    override fun onCreate(savedInstanceState: Bundle?) {

        // Setting content page.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_appointment_list)

        // Title for the page.
        supportActionBar?.title = "Appointments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        // Setting up recycler view.
        recyclerView = findViewById(R.id.studentAppointmentListRecyclerView)
        recyclerView.setHasFixedSize(true)
        studentAppointmentListAdapter = StudentAppointmentListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = studentAppointmentListAdapter

        // Fetching appointments of the current student.
        studentController.fetchAppointments(student.email.toString()) { success ->

            if (success) {
                studentAppointmentListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this,"Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show()
            }

        }

        // On click event for add appointment button.
        addAppointmentButton = findViewById(R.id.addAppointmentButton)
        addAppointmentButton.setOnClickListener {
            val advisorListIntent = Intent(this, StudentAppointmentAdvisorListActivity::class.java)
            startActivity(advisorListIntent)
        }

    }

}