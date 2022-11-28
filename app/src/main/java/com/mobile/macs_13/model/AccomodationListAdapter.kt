package com.mobile.macs_13.model

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.accomodation.AccomodationList
import com.mobile.macs_13.controller.accomodation.AdvisorAccomodation
// Accommodation adapter class to handle the requests shown to the advisor from the students

private val REQUESTER_NAME = "Student Name : "
private val REQUEST_DETAILS = "Request Details: "

class AccomodationListAdapter :
    RecyclerView.Adapter<AccomodationListAdapter.NotifViewHolder>() {

    var advisorAccomRequestModelList = AccomodationList.getAccomodations()


    inner class NotifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.requester_name)
        val userImage: ImageView = itemView.findViewById(R.id.requester_image)
        val studentCourse: TextView = itemView.findViewById(R.id.requester_course)
        val studentImpact: TextView = itemView.findViewById(R.id.request_details)
        var checkRequest : Button = itemView.findViewById(R.id.check_request)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.advisor_home_cards, parent, false)

        return NotifViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {

        val advisorAccomRequestModel = advisorAccomRequestModelList[position]

        Glide.with(holder.itemView)
            .load(advisorAccomRequestModel.imageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(holder.userImage);

        holder.name.text = REQUESTER_NAME + advisorAccomRequestModel.name
        holder.studentCourse.text = advisorAccomRequestModel.course
        holder.studentImpact.text = REQUEST_DETAILS + advisorAccomRequestModel.impact
        holder.checkRequest.setOnClickListener{ view ->
            onClickCheckRequest(advisorAccomRequestModel, view)
        }

    }

    @SuppressLint("SuspiciousIndentation")
    fun onClickCheckRequest(
        advisorAccomRequestModel: AdvisorAccomRequestModel,
        view: View
    ) {

     val advisorAccommodationIntent = Intent(view.context, AdvisorAccomodation::class.java)
        advisorAccommodationIntent.putExtra("advisorAccommodation",advisorAccomRequestModel )
        view.context.startActivity(advisorAccommodationIntent)

    }

    override fun getItemCount(): Int {
        return advisorAccomRequestModelList.size
    }



}
