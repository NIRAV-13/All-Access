package com.mobile.macs_13.com.mobile.macs_13.view.advisor

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
import com.mobile.macs_13.controller.accomodation.AdvisorAccomodation
import com.mobile.macs_13.model.AdvisorAccomRequestModel
import com.mobile.macs_13.model.StudentAccomRequestModel

private const val REQUESTER_NAME = "Student Name : "
private const val REQUEST_DETAILS = "Request Details: "

/**
 * Advisor Home Adapter - Adapter for the Advisor Home Accommodation Requests
 * @author Ankush Mudgal
 */
class AdvisorHomeAdapter(private val appReqList: ArrayList<StudentAccomRequestModel>) :
    RecyclerView.Adapter<AdvisorHomeAdapter.RequestViewHolder>() {

    /**
     * Request View Holder
     *
     * @param itemView
     */
    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Create Holders for the Recycler View fields
        val userImage: ImageView = itemView.findViewById(R.id.requester_image)
        val requesterName: TextView = itemView.findViewById(R.id.requester_name)
        val requesterCourse: TextView = itemView.findViewById(R.id.requester_course)
        val requestDetails: TextView = itemView.findViewById(R.id.request_details)
        val checkRequestButton: Button = itemView.findViewById(R.id.check_request)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        // Inflate Layout of the Item in Recycler View
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.advisor_home_cards, parent, false)

        return RequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {

        val currentRequest = appReqList[position]

        Log.d("FIX", currentRequest.toString())
        // fetch the Image to be displayed
        Glide.with(holder.itemView)
            .load(currentRequest.imageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(holder.userImage)

        //Populate the data for the Recycler View for current Notification
        holder.requesterName.text = REQUESTER_NAME + currentRequest.name
        holder.requesterCourse.text = currentRequest.course
        holder.requestDetails.text = REQUEST_DETAILS + currentRequest.impact
        holder.checkRequestButton.setOnClickListener { onClick(holder.itemView, currentRequest) }
    }

    private fun onClick(view: View, reqObject: StudentAccomRequestModel) {

        val intent = Intent(view.context, AdvisorAccomodation::class.java)
        Log.d("HERE", "$reqObject")
        val sendToAdvisor = AdvisorAccomRequestModel(
            uid = reqObject.uid,
            name = reqObject.name, email = reqObject.email,
            phone = reqObject.phone,
            program = reqObject.program,
            course = reqObject.course,
            year = reqObject.year,
            term = reqObject.term,
            docs = null, imageLink = reqObject.imageLink,
            impact = reqObject.impact,
            consent = reqObject.consent,
            status = reqObject.status,
            timeStamp = null, advisorName = null, advisorEmail = null,
            advisorImageLink = null,
            documentId = reqObject.requestID
        )
        intent.putExtra("advisorAccommodation", sendToAdvisor)
        view.context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return appReqList.size
    }

}