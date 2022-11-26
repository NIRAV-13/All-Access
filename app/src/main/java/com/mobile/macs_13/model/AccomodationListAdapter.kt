package com.mobile.macs_13.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.R


class AccomodationListAdapter(private val advisorAccomRequestModelList: MutableList<AdvisorAccomRequestModel>) :
    RecyclerView.Adapter<AccomodationListAdapter.NotifViewHolder>() {

    var onItemClick : ((AdvisorAccomRequestModel) -> Unit)? = null

    inner class NotifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.nameTextView)
        val program: TextView = itemView.findViewById(R.id.programeTextView)
        val year: TextView = itemView.findViewById(R.id.yearTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.accomodation_list_item, parent, false)

        return NotifViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {

        val advisorAccomRequestModel = advisorAccomRequestModelList[position]

        holder.name.text = advisorAccomRequestModel.name
        holder.program.text = advisorAccomRequestModel.program
        holder.year.text = advisorAccomRequestModel.year

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(advisorAccomRequestModel)
        }

    }

    override fun getItemCount(): Int {
        return advisorAccomRequestModelList.size
    }



}
