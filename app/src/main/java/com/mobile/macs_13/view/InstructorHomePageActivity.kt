package com.mobile.macs_13.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.R
import com.mobile.macs_13.com.mobile.macs_13.model.InstructorHomePageAdapter
import com.mobile.macs_13.com.mobile.macs_13.model.StudentWithAccommodationAdapter
import com.mobile.macs_13.controller.InstructorController
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAppointmentListAdapter
import com.mobile.macs_13.model.StudentWithAccommodationList

class InstructorHomePageActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var studentController: StudentController = StudentController()
    private lateinit var studentWithAccommodationAdapter: StudentWithAccommodationAdapter

    private var instructorController: InstructorController = InstructorController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_home_page)

        recyclerView = findViewById(R.id.instructorHomePageRecycler)
        recyclerView.setHasFixedSize(true)
        studentWithAccommodationAdapter = StudentWithAccommodationAdapter(StudentWithAccommodationList.getStudentsWithAccommodation())
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = studentWithAccommodationAdapter


        val user = User.getCurrentUserProfile()

        instructorController.fetchCurrentStudents(user.term.toString(),user.course.toString()) { success ->

            if (success) {
                studentWithAccommodationAdapter.notifyDataSetChanged()
            } else {
                Log.d("data", "something went wrong.")
            }
        }

    }
}