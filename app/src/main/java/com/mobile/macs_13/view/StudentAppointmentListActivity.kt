package com.mobile.macs_13.view

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.model.AdvisorList
import com.mobile.macs_13.model.AdvisorListAdapter
import com.mobile.macs_13.model.StudentAppointmentList
import com.mobile.macs_13.model.StudentAppointmentListAdapter

class StudentAppointmentListActivity : AppCompatActivity() {


    private lateinit var recyclerView : RecyclerView
    private var studentController: StudentController = StudentController()
    private lateinit var studentAppointmentListAdapter: StudentAppointmentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_student_appointment_list)
        recyclerView = findViewById(R.id.studentAppointmentListRecyclerView)
        recyclerView.setHasFixedSize(true)
        studentAppointmentListAdapter = StudentAppointmentListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = studentAppointmentListAdapter

        studentController.fetchAppointments("test@dal.ca") { success ->

            if(success){
                studentAppointmentListAdapter.notifyDataSetChanged()
            }
            else{
                Log.d("data","something went wrong.")
            }
        }

    }


}