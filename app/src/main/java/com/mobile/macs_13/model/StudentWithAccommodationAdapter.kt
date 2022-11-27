package com.mobile.macs_13.com.mobile.macs_13.model

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.databinding.AdvisorListItemBinding
import com.mobile.macs_13.model.AdvisorAccomRequestModel
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.StudentBookAppointment
import com.mobile.macs_13.databinding.StudentAppointmentListItemBinding


class StudentWithAccommodationAdapter(private val advisorAccomRequestModel: MutableList<AdvisorAccomRequestModel>) :
    RecyclerView.Adapter<StudentWithAccommodationAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentWithAccommodationAdapter.ViewHolder {

        return ViewHolder(
            StudentAppointmentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StudentWithAccommodationAdapter.ViewHolder, position: Int) {

        val accomRequestModel = advisorAccomRequestModel[position]
        holder.bindItem(accomRequestModel)
    }

    override fun getItemCount(): Int {
//        Log.d("size", "${advisors.size}")
        return advisorAccomRequestModel.size
    }

    inner class ViewHolder(val binding: StudentAppointmentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(advisorAccomRequestModel: AdvisorAccomRequestModel) {

//            Log.d("advisor", "$advisor")
//            binding.advisorEmailText.text = advisorAccomRequestModel.
            binding.advisorEmailText.text = advisorAccomRequestModel.email

            binding.selectAdvisor.setOnClickListener { onClick(advisor) }
        }


        fun onClick(advisor: UserProfile) {

            val intent = Intent(binding.root.context, StudentBookAppointment::class.java)
            intent.putExtra("advisor", advisor)
            binding.root.context.startActivity(intent)

        }

    }
}