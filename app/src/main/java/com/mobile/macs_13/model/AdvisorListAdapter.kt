package com.mobile.macs_13.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.mobile.macs_13.databinding.AdvisorListItemBinding
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.com.mobile.macs_13.view.student.StudentBookAppointment
import com.bumptech.glide.Glide
import com.mobile.macs_13.R


// Adapter class to show list of advisors in recycler view.
class AdvisorListAdapter(private val advisors: MutableList<UserProfile>) : RecyclerView.Adapter<AdvisorListAdapter.ViewHolder>() {

    // Overriding the onCreateViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvisorListAdapter.ViewHolder {
        return ViewHolder(
            AdvisorListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Overriding on onBindViewHolder.
    override fun onBindViewHolder(holder: AdvisorListAdapter.ViewHolder, position: Int) {
        val advisor = advisors[position]
        holder.bindItem(advisor, holder)
    }

    // Overriding on getItemCount.
    override fun getItemCount(): Int {
        return advisors.size
    }

    // Inner View holder class to bind data.
    inner class ViewHolder(private val binding: AdvisorListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Binding all items.
        fun bindItem(advisor: UserProfile, holder: ViewHolder) {

            val view = holder.itemView.findViewById<ImageView>(R.id.advisorListImage)
            Glide.with(holder.itemView)
                .load(advisor.imageLink)
                .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
                .into(view);

            binding.advisorNameText.text = advisor.name
            binding.advisorEmailText.text = advisor.email
            binding.selectAdvisor.setOnClickListener { onClick(advisor) }
        }

        // Onclick event for select advisor.
        private fun onClick(advisor: UserProfile) {
            val intent = Intent(binding.root.context, StudentBookAppointment::class.java)
            intent.putExtra("advisor", advisor)
            binding.root.context.startActivity(intent)
        }

    }
}

