package com.mobile.macs_13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class AdvisorHomeAdapter(private val appReqList: ArrayList<AppointmentRequest>) :
    RecyclerView.Adapter<AdvisorHomeAdapter.RequestViewHolder>() {

    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userImage: ImageView = itemView.findViewById(R.id.requester_image)
        val requesterName: TextView = itemView.findViewById(R.id.requester_name)
        val requesterCourse: TextView = itemView.findViewById(R.id.requester_course)
        val requestDetails: TextView = itemView.findViewById(R.id.request_details)
        val checkRequestButton: Button = itemView.findViewById<Button>(R.id.check_request)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.advisor_home_cards, parent, false)

        return RequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {

        val currentRequest = appReqList[position]

        holder.userImage.setImageResource(R.drawable.ic_avatar)
        holder.requesterName.text = currentRequest.requesterName
        holder.requesterCourse.text = currentRequest.requesterCourse
        holder.requestDetails.text = currentRequest.requestDetails
        //todo Add logic to handle button click on Card.
    }

    override fun getItemCount(): Int {
        return appReqList.size
    }

}