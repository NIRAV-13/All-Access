package com.mobile.macs_13.view


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobile.macs_13.R
import java.util.*


class StudentBookAppointment : AppCompatActivity(){

    private lateinit var lastSelectedCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_book_appointment)


        val spinnerLanguages = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.timeslots,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguages.adapter = adapter;

        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        lastSelectedCalendar = Calendar.getInstance();

        calendarView.minDate = lastSelectedCalendar.timeInMillis - 1000

        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener { _, year, month, dayOfMonth ->
            val checkCalendar = Calendar.getInstance()
            checkCalendar[year, month] = dayOfMonth
            if (checkCalendar.equals(lastSelectedCalendar)) return@OnDateChangeListener
            if (checkCalendar[Calendar.DAY_OF_WEEK] === Calendar.SUNDAY || checkCalendar[Calendar.DAY_OF_WEEK] === Calendar.SATURDAY) {
                calendarView.date =
                    lastSelectedCalendar.timeInMillis
            } else {
                lastSelectedCalendar = checkCalendar
            }
        })


        val submitButton = findViewById<Button>(R.id.submitButtonToBookAppointment)

        submitButton.setOnClickListener {

            val date = calendarView.date
            val hour:Int
            val minute:Int

        }

    }

}