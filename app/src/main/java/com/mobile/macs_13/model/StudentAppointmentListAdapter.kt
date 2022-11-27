package com.mobile.macs_13.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.databinding.StudentAppointmentListItemBinding
import java.text.SimpleDateFormat

// Student appointment list adapter class to show list in recycler view.
class StudentAppointmentListAdapter() : RecyclerView.Adapter<StudentAppointmentListAdapter.ViewHolder>(){

    // Fetching all appointment details.
    private val appointmentDetailList = StudentAppointmentList.getAppointments();

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
        holder.bindItem(appointmentDetail)
    }

    // Overriding getItemCount.
    override fun getItemCount(): Int {
        return appointmentDetailList.size
    }

    // Inner class ViewHolder to bind items.
    inner class ViewHolder(private val binding: StudentAppointmentListItemBinding): RecyclerView.ViewHolder(binding.root){

        // Binding each items.
        fun bindItem(appointmentDetail: AppointmentDetails){

            binding.advisorEmailText.text = appointmentDetail.advisorEmail

            val pattern = "MM-dd-yyyy hh:mm:ss a"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date = simpleDateFormat.format(appointmentDetail.appointmentStartTime)

            binding.appointmentTime.text = date.toString()
            binding.remindButton.setOnClickListener { onClick(appointmentDetail) }

        }

        // onClick event for each remind button.
        private fun onClick(appointmentDetail: AppointmentDetails) {
            AddRemindNotification.sendNotification(appointmentDetail, binding.root.context)
        }

    }

}