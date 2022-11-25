package com.mobile.macs_13

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

class AdvisorHomeAdapter(private val appReqList: ArrayList<AccomRequest>) :
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

        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        val currentUserID = "1001"
        //mAuth.currentUser?.uid


        db.collection("StudentProfileImages").document(currentUserID)
            .addSnapshotListener(object : EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Log.d(
                            "Error",
                            "Some Error in Connection to FireStore ${error.message.toString()}"
                        )
                        return
                    }

                    if (value != null) {
                        if (value.id == currentUserID) {
                            currentRequest.imageLink = value.data?.get("link").toString()
                            Glide
                                .with(holder.itemView)
                                .load(currentRequest.imageLink)
                                .centerCrop()
                                .into(holder.userImage);
                        }
                    }
                }
            })

        //holder.userImage.setImageResource(R.drawable.ic_avatar)
        holder.requesterName.text = currentRequest.requesterName
        holder.requesterCourse.text = currentRequest.requesterCourse
        holder.requestDetails.text = currentRequest.requestDetails
        holder.checkRequestButton.setOnClickListener { onClick(holder.itemView) }
    }

    private fun onClick(view: View) {

        view.findNavController().navigate(R.id.requestDetailsFragment)

    }

    override fun getItemCount(): Int {
        return appReqList.size
    }

}