package com.mobile.macs_13.model

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import com.mobile.macs_13.databinding.AdvisorListItemBinding

import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.view.StudentBookAppointment

class AdvisorListAdapter(private val advisors: MutableList<UserProfile>) :
    RecyclerView.Adapter<AdvisorListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdvisorListAdapter.ViewHolder {

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
        Log.d("size", "${advisors.size}")
        return advisors.size
    }

    inner class ViewHolder(val binding: AdvisorListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(advisor: UserProfile) {

            Log.d("advisor", "$advisor")
            binding.advisorNameText.text = advisor.name
            binding.advisorEmailText.text = advisor.email

            binding.selectAdvisor.setOnClickListener { onClick(advisor) }
        }


        fun onClick(advisor: UserProfile) {

            val intent = Intent(binding.root.context, StudentBookAppointment::class.java)
            intent.putExtra("advisor", advisor)
            binding.root.context.startActivity(intent)

        }

    }
}