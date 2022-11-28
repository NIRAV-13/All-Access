package com.mobile.macs_13.view


import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.model.AvailableAppointmentList
import com.mobile.macs_13.model.SlotDetail
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.AppointmentDetails
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*


// Activity class for student book appointment.
class StudentBookAppointment : AppCompatActivity(){

    // Declaring required private attributes.
    private lateinit var lastSelectedCalendar: Calendar
    private lateinit var calendarView: CalendarView
    private lateinit var submitButton: Button
    private var timeOptions : ArrayList<String> = ArrayList()
    private lateinit var spinnerSlots: Spinner
    private val studentController: StudentController = StudentController()
    private lateinit var slotDetail: SlotDetail
    private var slotDetailMap : HashMap<String, SlotDetail> = HashMap()
    private val student = User.getCurrentUserProfile()

    // Set for required time.
    private fun setCalender(){
        calendarView = findViewById(R.id.calendarView)
        lastSelectedCalendar = Calendar.getInstance();
        calendarView.minDate = lastSelectedCalendar.timeInMillis - 1000
        calendarView.maxDate = System.currentTimeMillis() + 1209600000
    }

    // Set the available slots for the selected date
    @RequiresApi(33)
    private fun setAvailableSlots(startTime: Long, midNightEndTime: Long) {

        // Fetching selected advisor
        val advisor = intent.getSerializableExtra("advisor") as UserProfile?

        // Fetching availability of selected advisor.
        studentController.fetchAvailability(advisor?.email.toString(), startTime, midNightEndTime) { status ->

            if(status){

                // Iterating through availability list and adding to slots.
                val availabilityList = AvailableAppointmentList.getAvailability()
                for (i in availabilityList){

                    val f: NumberFormat = DecimalFormat("00")

                    val startDate = Date((i.startTime?.seconds)!! *1000)
                    val endDate = Date((i.endTime?.seconds)!!*1000)

                    val startHour = f.format(startDate.hours)
                    val startMinute = f.format(startDate.minutes)

                    val endHour = f.format(endDate.hours)
                    val endMinute = f.format(endDate.minutes)

                    val availabilityId = i.availabilityId

                    slotDetail = SlotDetail(availabilityId, startHour, startMinute, endHour, endMinute)

                    val option = slotDetail.toString()
                    timeOptions.add(option)
                    slotDetailMap[option] = slotDetail

                }

                // Adding slots to list of available slots.
                val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeOptions)
                spinnerSlots = findViewById(R.id.spinner)
                spinnerSlots.adapter = spinnerAdapter;

            }
            else{
                Toast.makeText(this,"Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show()
            }

        }
    }


    // Activity onCreate event.
    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {

        // Setting up the view.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_book_appointment)

        // Setting calender for two weeks.
        setCalender()

        // On date change event to set slots for the selected date.
        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener { _, year, month, dayOfMonth ->

            timeOptions.clear()
            slotDetailMap.clear()

            spinnerSlots = findViewById(R.id.spinner)
            spinnerSlots.adapter = null

            val checkCalendar = Calendar.getInstance()
            checkCalendar[year, month] = dayOfMonth

            if (checkCalendar.equals(lastSelectedCalendar)) return@OnDateChangeListener

            if (checkCalendar[Calendar.DAY_OF_WEEK] === Calendar.SUNDAY || checkCalendar[Calendar.DAY_OF_WEEK] === Calendar.SATURDAY) {
                calendarView.date =
                    lastSelectedCalendar.timeInMillis
            } else {
                lastSelectedCalendar = checkCalendar

                val finalMonth = month.inc()
                val midNightDate = LocalDate.of(year,finalMonth,dayOfMonth).atTime(LocalTime.MIDNIGHT)
                val zoneId = ZoneId.systemDefault();
                val midNightStartTime = midNightDate.atZone(zoneId).toEpochSecond() *  1000
                val midNightEndTime = midNightStartTime + (24* 60 *60 * 1000)

                setAvailableSlots(midNightStartTime, midNightEndTime)


            }


        })


        // Set on click event on submit button.
        submitButton = findViewById(R.id.submitButtonToBookAppointment)
        submitButton.setOnClickListener {
            onSubmitClick()
        }

    }

    // On submit click button.
    private fun onSubmitClick(){

        val selectedDate = Date(lastSelectedCalendar.time.time)
        val calendar: Calendar = GregorianCalendar()
        calendar.time = selectedDate

        val year = calendar.get(Calendar.YEAR);
        val month = calendar.get(Calendar.MONTH) + 1;
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        spinnerSlots = findViewById(R.id.spinner)

        var selectedSlot = spinnerSlots.selectedItem.toString()
        val slotDetail = slotDetailMap[selectedSlot]

        val startCal = Calendar.getInstance()
        startCal[Calendar.HOUR_OF_DAY] = slotDetail?.startHour?.toInt()!!
        startCal[Calendar.MINUTE] = slotDetail?.startMinute?.toInt()!!
        startCal[Calendar.SECOND] = 0
        startCal[Calendar.MILLISECOND] = 0
        startCal[Calendar.YEAR] = year
        startCal[Calendar.MONTH] = month - 1
        startCal[Calendar.DAY_OF_MONTH] = day

        val startTime = startCal.time

        val endCal = Calendar.getInstance()
        endCal[Calendar.HOUR_OF_DAY] = slotDetail?.endHour?.toInt()!!
        endCal[Calendar.MINUTE] = slotDetail?.endMinute?.toInt()!!
        endCal[Calendar.SECOND] = 0
        endCal[Calendar.MILLISECOND] = 0
        endCal[Calendar.YEAR] = year
        endCal[Calendar.MONTH] = month - 1
        endCal[Calendar.DAY_OF_MONTH] = day

        val endTime = endCal.time

        val advisor = intent.getSerializableExtra("advisor") as UserProfile?

        val appointmentDetails = AppointmentDetails(
            advisor?.name,
            advisor?.email,
            advisor?.phone,
            advisor?.imageLink,
            student.email,
            student.name,
            student.phone,
            student.program,
            student.course,
            student.year,
            student.imageLink,
            startTime,
            endTime,
            true
        )

        val studentBookAppointmentFormIntent = Intent(this, StudentBookAppointmentFormActivity::class.java)
        studentBookAppointmentFormIntent.putExtra("appointmentDetails", appointmentDetails)
        studentBookAppointmentFormIntent.putExtra("slotDetail", slotDetail)
        startActivity(studentBookAppointmentFormIntent)

    }

}

