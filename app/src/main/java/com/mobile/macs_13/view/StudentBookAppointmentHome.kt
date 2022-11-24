package com.mobile.macs_13.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.model.AdvisorList
import com.mobile.macs_13.model.AdvisorListAdapter

class StudentBookAppointmentHome : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private var studentController: StudentController = StudentController()
    private lateinit var advisorListAdapter: AdvisorListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_book_appointment_home)

        recyclerView = findViewById(R.id.advisorListRecyclerView)
        recyclerView.setHasFixedSize(true)
        advisorListAdapter = AdvisorListAdapter(AdvisorList.getAdvisors())
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = advisorListAdapter

        studentController.fetchAdvisorList { success ->
            advisorListAdapter.notifyDataSetChanged()
        }

    }

}