package com.mobile.macs_13.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.macs_13.R
import com.mobile.macs_13.databinding.StudentAppointmentListItemBinding
import java.text.SimpleDateFormat

// Student appointment list adapter class to show list in recycler view.
class StudentAppointmentListAdapter() : RecyclerView.Adapter<StudentAppointmentListAdapter.ViewHolder>(){

    // Fetching all appointment details.
    private val appointmentDetailList = StudentAppointmentList.getAppointments();

    // Store reminder notification.
    private val reminderList = mutableListOf<AppointmentDetails>()

    // Overriding onCreateViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAppointmentListAdapter.ViewHolder {

        return ViewHolder(
            StudentAppointmentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Overriding onBindViewHolder.
    override fun onBindViewHolder(holder: StudentAppointmentListAdapter.ViewHolder, position: Int) {
        val appointmentDetail = appointmentDetailList[position]
        holder.bindItem(appointmentDetail, holder)
    }

    // Overriding getItemCount.
    override fun getItemCount(): Int {
        return appointmentDetailList.size
    }

    // Inner class ViewHolder to bind items.
    inner class ViewHolder(private val binding: StudentAppointmentListItemBinding): RecyclerView.ViewHolder(binding.root){

        // Binding each items.
        fun bindItem(appointmentDetail: AppointmentDetails, holder: ViewHolder) {

            binding.advisorEmailText.text = appointmentDetail.advisorEmail
            val view = holder.itemView.findViewById<ImageView>(R.id.advisorImageStudentAppointment)
            Glide.with(holder.itemView)
                .load(appointmentDetail.advisorImageLink)
                .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
                .into(view);
            val pattern = "MM-dd-yyyy hh:mm:ss a"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date = simpleDateFormat.format(appointmentDetail.appointmentStartTime)

            binding.appointmentTime.text = date.toString()
            binding.remindButton.setOnClickListener { onClick(appointmentDetail, holder.itemView) }

        }

        // onClick event for each remind button.
        private fun onClick(appointmentDetail: AppointmentDetails, view: View) {

            if(!reminderList.contains(appointmentDetail)){
                reminderList.add(appointmentDetail)
                AddRemindNotification.sendNotification(appointmentDetail, view.context)
                PushStudentNotification.pushStudentHomeNotification(
                    StudentNotificationData("Reminder Set!",
                        "Appointment reminder with ${appointmentDetail.advisorName} on ${appointmentDetail.appointmentStartTime} to ${appointmentDetail.appointmentEndTime} has been successful. You will be notified 30 mins before your appointment"))
                Toast.makeText(view.context, "Reminder Set for 30 mins before your appointment.", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(view.context, "The reminded has already been set for this appointment.", Toast.LENGTH_LONG).show()
            }

        }

    }

}