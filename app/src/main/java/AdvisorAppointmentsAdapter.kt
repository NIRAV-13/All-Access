package com.mobile.macs_13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.model.AppointmentDetails
import java.text.DateFormat
import java.util.*

private val HEADER_MESSAGE = "Upcoming Appointment on "

class AdvisorAppointmentsAdapter(private val appointmentsList: ArrayList<AppointmentDetails>) :
    RecyclerView.Adapter<AdvisorAppointmentsAdapter.AppointmentViewHolder>() {

    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val appointmentDate: TextView = itemView.findViewById(R.id.appointment_date_header)
        val appointmentAttendeeAdvisor: TextView =
            itemView.findViewById(R.id.appointment_attendee_advisor)
        val appointmentAttendeeStudent: TextView =
            itemView.findViewById(R.id.appointment_attendee_student)
        val appointmentTime: TextView =
            itemView.findViewById(R.id.appointment_time)
        val reminderButton: ImageButton =
            itemView.findViewById<ImageButton>(R.id.appointment_advisor_reminder)
        val chatButton: ImageButton = itemView.findViewById<ImageButton>(R.id.appointment_advisor_chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.advisor_appointment_card, parent, false)

        return AppointmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val currentAppointment = appointmentsList[position]

        holder.appointmentDate.text = HEADER_MESSAGE + getAppointmentData(currentAppointment.appointmentStartTime!!)
        holder.appointmentAttendeeAdvisor.text = currentAppointment.advisorName
        holder.appointmentAttendeeStudent.text = currentAppointment.studentName
        holder.appointmentTime.text = getAppointmentTime(currentAppointment.appointmentStartTime!!, currentAppointment.appointmentEndTime!!)

        // todo holder.reminderButton
        // todo holder.chatButton
    }

    override fun getItemCount(): Int {
        return appointmentsList.size
    }

    private fun getAppointmentData(date: Date): String {

        val locale = Locale("en", "EN")
        val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
        return dateFormat.format(date)
    }

    private fun getAppointmentTime(sdate: Date, edate: Date): String {

        val locale = Locale("en", "EN")
        val dateFormat: DateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale)
        return "From: ${dateFormat.format(sdate)} - ${dateFormat.format(edate)}"
    }

}