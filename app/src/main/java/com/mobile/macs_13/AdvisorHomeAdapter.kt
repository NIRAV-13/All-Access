package com.mobile.macs_13

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAccomRequestModel
import com.mobile.macs_13.view.AccomodationListActivity
import com.mobile.macs_13.view.StudentBookAppointment

private val REQUESTER_NAME = "Student Name : "
private val REQUEST_DETAILS = "Request Details: "

class AdvisorHomeAdapter(private val appReqList: ArrayList<StudentAccomRequestModel>) :
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

        Glide.with(holder.itemView)
            .load(currentRequest.imageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(holder.userImage);
        holder.requesterName.text = REQUESTER_NAME + currentRequest.name
        holder.requesterCourse.text = currentRequest.course
        holder.requestDetails.text = REQUEST_DETAILS + currentRequest.impact
        holder.checkRequestButton.setOnClickListener { onClick(holder.itemView) }
    }

    private fun onClick(view: View) {

        val intent = Intent(view.context, AccomodationListActivity:: class.java)
        view.context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return appReqList.size
    }

}