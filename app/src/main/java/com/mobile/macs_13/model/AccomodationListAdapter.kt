package com.mobile.macs_13.model

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
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

private val REQUESTER_NAME = "Student Name : "
private val REQUEST_DETAILS = "Request Details: "
private val COURSE_DETAILS = "Course Details:"

class AccomodationListAdapter :
    RecyclerView.Adapter<AccomodationListAdapter.NotifViewHolder>() {

    var advisorAccomRequestModelList = AccomodationList.getAccomodations()
    var onItemClick : ((AdvisorAccomRequestModel) -> Unit)? = null


    inner class NotifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.requester_name)
        val userImage: ImageView = itemView.findViewById(R.id.requester_image)
        val studentCourse: TextView = itemView.findViewById(R.id.requester_course)
        val studentImpact: TextView = itemView.findViewById(R.id.request_details)
        var checkRequest : Button = itemView.findViewById(R.id.check_request)
//        var view :View = itemView

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
            .load(advisorAccomRequestModel.studentImageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(holder.userImage);

        holder.name.text = REQUESTER_NAME + advisorAccomRequestModel.studentName
        holder.studentCourse.text = advisorAccomRequestModel.studentCourse
        holder.studentImpact.text = REQUEST_DETAILS + advisorAccomRequestModel.studentImpact
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
        Log.d("XYZ","${advisorAccomRequestModelList.size}")
        return advisorAccomRequestModelList.size
    }



}
