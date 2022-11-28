package com.mobile.macs_13.com.mobile.macs_13.model


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.model.AdvisorAccomRequestModel

import com.mobile.macs_13.databinding.StudentWithAccommodationItemBinding


class StudentWithAccommodationAdapter(private val advisorAccomRequestModel: MutableList<AdvisorAccomRequestModel>) :
    RecyclerView.Adapter<StudentWithAccommodationAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentWithAccommodationAdapter.ViewHolder {

        return ViewHolder(
            StudentWithAccommodationItemBinding.inflate(
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
        return advisorAccomRequestModel.size
    }

    inner class ViewHolder(val binding: StudentWithAccommodationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(advisorAccomRequestModel: AdvisorAccomRequestModel) {

            Log.d("advisor", "$advisorAccomRequestModel")

            binding.studentEmailInstructorPage.text = advisorAccomRequestModel.email
            binding.studentNameInstructorPage.text = advisorAccomRequestModel.name
            binding.studentCourseInstructorPage.text = advisorAccomRequestModel.course
            binding.studentPhoneInstructorPage.text = advisorAccomRequestModel.phone
            binding.studentAccommodationStatusInstructorPage.text = advisorAccomRequestModel.status
            binding.advisorEmailInstructorPage.text = advisorAccomRequestModel.advisorEmail
            binding.advisorNameInstructorPage.text = advisorAccomRequestModel.advisorName

        }

    }
}