package com.mobile.macs_13.model

import android.content.Intent
import android.util.Log
import com.mobile.macs_13.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity

import com.mobile.macs_13.databinding.AdvisorListItemBinding
import com.mobile.macs_13.databinding.ActivityStudentBookAppointmentHomeBinding

import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.view.StudentBookAppointment
import com.mobile.macs_13.view.StudentBookAppointmentHome

class AdvisorListAdapter(private val advisors: MutableList<Advisor>) :RecyclerView.Adapter<AdvisorListAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvisorListAdapter.ViewHolder {

        return ViewHolder(
            AdvisorListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdvisorListAdapter.ViewHolder, position: Int) {

        val advisor = advisors[position]
        holder.bindItem(advisor)
    }

    override fun getItemCount(): Int {
        return advisors.size
    }

    inner class ViewHolder(val binding: AdvisorListItemBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bindItem(advisor: Advisor){

                binding.nameText.text = advisor.advisorName
                binding.emailText.text = advisor.advisorEmail

                binding.button2.setOnClickListener { onClick(advisor) }
            }


            fun onClick(advisor: Advisor) {

                val intent = Intent(binding.root.context, StudentBookAppointment:: class.java)
                intent.putExtra("advisorEmail", advisor.advisorEmail)
                binding.root.context.startActivity(intent)

            }

    }
}