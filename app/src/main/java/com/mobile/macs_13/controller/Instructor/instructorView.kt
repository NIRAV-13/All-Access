package com.mobile.macs_13.controller.Instructor

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.FileOperation.UploadFile

class instructorView : AppCompatActivity() {
    private lateinit var selectsemdropdown: Spinner
    private lateinit var selectcoursedropdown: Spinner
    lateinit var  searchbutton : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_view)

        searchbutton = findViewById(R.id.button6)

        selectsemdropdown=findViewById(R.id.spinnerSelectSemester);
        var adaptersem = ArrayAdapter.createFromResource(this, R.array.selectSemInstructor,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        adaptersem.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        selectsemdropdown.setAdapter(adaptersem);

        selectcoursedropdown=findViewById(R.id.spinnerSelectCourse);
        var adaptercourse = ArrayAdapter.createFromResource(this, R.array.selectCourseInstructor,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        adaptercourse.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        selectcoursedropdown.setAdapter(adaptercourse);

        searchbutton.setOnClickListener {

            val selectedSem = selectsemdropdown.selectedItem.toString()
            val selectedCourse = selectcoursedropdown.selectedItem.toString()

            val searchDetailsIntent = Intent(this, InstructorCourseView::class.java)
            searchDetailsIntent.putExtra("selectedSem",selectedSem)
            searchDetailsIntent.putExtra("selectedCourse",selectedCourse)
            startActivity(searchDetailsIntent)
        }
    }
}