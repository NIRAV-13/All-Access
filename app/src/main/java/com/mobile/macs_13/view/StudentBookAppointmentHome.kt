package com.mobile.macs_13.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAppointmentListAdapter

class StudentBookAppointmentHome : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var studentController: StudentController = StudentController()
    private lateinit var studentAppointmentListAdapter: StudentAppointmentListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_student_appointment_list)
        supportActionBar?.title = "Appointments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.studentAppointmentListRecyclerView)
        recyclerView.setHasFixedSize(true)
        studentAppointmentListAdapter = StudentAppointmentListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = studentAppointmentListAdapter

        val user = User.getCurrentUserProfile()

        studentController.fetchAppointments(user.email.toString()) { success ->

            if (success) {
                studentAppointmentListAdapter.notifyDataSetChanged()
            } else {
                Log.d("data", "something went wrong.")
            }
        }

        val addAppointmentButton = findViewById<FloatingActionButton>(R.id.addAppointmentButton)

        addAppointmentButton.setOnClickListener {
            val advisorListIntent = Intent(this, StudentAppointmentAdvisorListActivity::class.java)
            startActivity(advisorListIntent)
        }

    }

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}