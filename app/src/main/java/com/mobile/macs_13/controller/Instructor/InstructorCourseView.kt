//package com.mobile.macs_13.controller.Instructor
//
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.graphics.Color
//import android.os.Bundle
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.widget.TableLayout
//import android.widget.TableRow
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.mobile.macs_13.R
//import com.mobile.macs_13.controller.InstructorController
//import com.mobile.macs_13.model.StudentAccomRequestModel
//import com.mobile.macs_13.model.StudentWithAccommodationList
//import org.w3c.dom.Text
//
//
//class InstructorCourseView : AppCompatActivity() {
//
//    private var viewID = 0;
//
//
//    @SuppressLint("ResourceType", "SetTextI18n")
//    fun storeStudentAccommodationDetails(studentWithAccommodation: MutableList<StudentAccomRequestModel>) {
//
//
//        val stk = findViewById<View>(R.id.tableLayout) as TableLayout
//        val tbrow0 = TableRow(this)
//
//        var t = 0
//
//        val tv0 = TextView(this)
//        tv0.text = " Name "
//        tv0.setTextColor(Color.BLACK)
//        tv0.setPadding(4, 4, 4, 4)
//        tbrow0.addView(tv0)
//        tv0.setBackgroundResource(R.drawable.tableboarder)
//
//
//        val tv1 = TextView(this)
//        tv1.text = " Term "
//        tv1.setTextColor(Color.BLACK)
//        tbrow0.addView(tv1)
//        tv1.setBackgroundResource(R.drawable.tableboarder)
//        tv1.setPadding(4, 4, 4, 4)
//
//        val tv2 = TextView(this)
//        tv2.text = " Year "
//        tv2.setTextColor(Color.BLACK)
//        tbrow0.addView(tv2)
//        tv2.setBackgroundResource(R.drawable.tableboarder)
//        tv2.setPadding(4, 4, 4, 4)
//
//        val tv3 = TextView(this)
//        tv3.text = " Course "
//        tv3.setTextColor(Color.BLACK)
//        tbrow0.addView(tv3)
//        tv3.setBackgroundResource(R.drawable.tableboarder)
//        tv3.setPadding(4, 4, 4, 4)
//
//
//        val tv4 = TextView(this)
//        tv4.text = " More details "
//        tv4.setTextColor(Color.BLACK)
//        tbrow0.addView(tv4)
//        tv4.setBackgroundResource(R.drawable.tableboarder)
//        tv4.setPadding(4, 4, 4, 4)
//        stk.addView(tbrow0)
//
//        tv4.setTextSize(1, 17F)
//        tv3.setTextSize(1, 17F)
//        tv2.setTextSize(1, 17F)
//        tv1.setTextSize(1, 17F)
//        tv0.setTextSize(1, 17F)
//
//
//        for (i in studentWithAccommodation)
//        {
//
//            val tbrow = TableRow(this)
//            val t1v = TextView(this)
//            t1v.text = i.name
//            t1v.setTextColor(Color.BLACK)
//            t1v.gravity = Gravity.CENTER
//            t1v.setBackgroundResource(R.drawable.tableboarder)
//            t1v.setPadding(4, 4, 4, 4)
//
//            tbrow.addView(t1v)
//            val t2v = TextView(this)
//            t2v.text = i.term
//            t2v.setTextColor(Color.BLACK)
//            t2v.gravity = Gravity.CENTER
//            tbrow.addView(t2v)
//            t2v.setBackgroundResource(R.drawable.tableboarder)
//            t2v.setPadding(4, 4, 4, 4)
//
//
//            val t3v = TextView(this)
//            t3v.text = i.year
//            t3v.setTextColor(Color.BLACK)
//            t3v.setTextSize(1, 17F)
//            t3v.gravity = Gravity.CENTER
//            tbrow.addView(t3v)
//            t3v.setBackgroundResource(R.drawable.tableboarder)
//            t3v.setPadding(4, 4, 4, 4)
//
//            if (t == 1)
//            {
//                var term: TextView = findViewById(R.id.term)
//                term.setText("Student Term: "+i?.term)
//            }
//
//
//            val t4v = TextView(this)
//            t4v.text = i.course
//            t4v.setTextColor(Color.BLACK)
//            t4v.gravity = Gravity.CENTER
//            tbrow.addView(t4v)
//            t4v.setPadding(4, 4, 4, 4)
//            t4v.setBackgroundResource(R.drawable.tableboarder)
//
//
//            val t5v = TextView(this)
//            t5v.text = " Click "
//            t5v.setTextColor(Color.BLACK)
//            t5v.gravity = Gravity.CENTER
//            tbrow.addView(t5v)
//            t5v.setPadding(4, 4, 4, 4)
//            tbrow.id = viewID
//            t5v.setOnClickListener { arg0 ->
//                var instructorController = InstructorController()
//                var id = tbrow.id
//                var studentDetails = StudentWithAccommodationList.getStudentDetails(id)
//
//                instructorController.getAccommodationDetails(
//                    studentDetails.uid.toString(),
//                    studentDetails.course.toString(),
//                    studentDetails.term.toString()
//                ) { studentAccommodationDetails ->
//                    val instructorStudentAccommodationIntent =
//                        Intent(arg0?.context, InstructorStudentAccomodation::class.java)
//                    instructorStudentAccommodationIntent.putExtra(
//                        "studentDetails",
//                        studentAccommodationDetails
//                    )
//                    startActivity(instructorStudentAccommodationIntent)
//                }
//            }
//            t4v.setTextSize(1, 17F)
//            t2v.setTextSize(1, 17F)
//            t1v.setTextSize(1, 17F)
//            t5v.setTextSize(1, 17F)
//
//            t5v.setBackgroundResource(R.drawable.tableboarder)
//            stk.addView(tbrow)
//
//            viewID = viewID.inc()
//            t += 1
//        }
//    }
//
//    @SuppressLint("MissingInflatedId")
//    lateinit var studentNav: TextView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_instructor_course_view)
//
//        val sem = this.intent.getStringExtra("selectedSem")
//
//        val course = this.intent.getStringExtra("selectedCourse")
//
//        val instructorController = InstructorController()
//
//        Log.d("XYZ", "$sem")
//
//        Log.d("XYZ", "$course")
//
//
//}
