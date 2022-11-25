package com.mobile.macs_13.model

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.view.StudentBookAppointment
import com.mobile.macs_13.databinding.StudentAppointmentListItemBinding;

class StudentAppointmentListAdapter() : RecyclerView.Adapter<StudentAppointmentListAdapter.ViewHolder>(){

    private val appointmentDetailList = StudentAppointmentList.getAppointments();


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAppointmentListAdapter.ViewHolder {

        return ViewHolder(
            StudentAppointmentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StudentAppointmentListAdapter.ViewHolder, position: Int) {
        val appointmentDetail = appointmentDetailList[position]
        holder.bindItem(appointmentDetail)
    }

    override fun getItemCount(): Int {

        return appointmentDetailList.size
    }

    inner class ViewHolder(private val binding: StudentAppointmentListItemBinding):
        RecyclerView.ViewHolder(binding.root){


        fun bindItem(appointmentDetail: AppointmentDetails){

            binding.advisorEmailText.text = appointmentDetail.advisorEmail
            binding.appointmentTime.text = appointmentDetail.startTime.toString()

            binding.remindButton.setOnClickListener { onClick(appointmentDetail) }
        }


        fun onClick(appointmentDetail: AppointmentDetails) {

            AddRemindNotification.sendNotification(appointmentDetail, binding.root.context)
        }

    }
}