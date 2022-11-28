package com.mobile.macs_13.com.mobile.macs_13.view.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.R
import com.mobile.macs_13.model.StudentNotificationData

/**
 * Student Home Adapter - Adapter for the Student Home Notification
 *
 * @property notifList
 * @author Ankush Mudgal
 */
class StudentHomeAdapter(private val notifList: ArrayList<StudentNotificationData>) :
    RecyclerView.Adapter<StudentHomeAdapter.NotifyViewHolder>() {

    /**
     * Notify View Holder
     * @param itemView
     */
    inner class NotifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Create Holders for the Recycler View fields
        val notifyTitle: TextView = itemView.findViewById(R.id.student_notif_title)
        val notifyText: TextView = itemView.findViewById(R.id.student_notif_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {

        // Inflate Layout of the Item in Recycler View
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_home_cards, parent, false)

        return NotifyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {

        val currentNotification = notifList[position]

        //Populate the data for the Recycler View for current Notification
        holder.notifyTitle.text = currentNotification.notifTitle
        holder.notifyText.text = currentNotification.notifText
    }

    override fun getItemCount(): Int {
        return notifList.size
    }


}