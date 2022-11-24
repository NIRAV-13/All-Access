package com.mobile.macs_13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.model.StudentNotificationData

class StudentHomeAdapter(private val notifList: ArrayList<StudentNotificationData>) :
    RecyclerView.Adapter<StudentHomeAdapter.NotifViewHolder>() {

    inner class NotifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val notifTitle: TextView = itemView.findViewById(R.id.student_notif_title)
        val notifText: TextView = itemView.findViewById(R.id.student_notif_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_home_cards, parent, false)

        return NotifViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {

        val currentNotification = notifList[position]

        holder.notifTitle.text = currentNotification.notifTitle
        holder.notifText.text = currentNotification.notifText
    }

    override fun getItemCount(): Int {
        return notifList.size
    }


}