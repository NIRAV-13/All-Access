package com.mobile.macs_13.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.macs_13.R
import com.mobile.macs_13.model.UserDemoModel
import com.mobile.macs_13.view.ChatView

class UserAdapter(val context: ChatView, val userList:ArrayList<UserDemoModel>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val textName= itemView.findViewById<TextView>(R.id.chat_name)
    }

    // to inflate the recycler view with the users and show in the application
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View= LayoutInflater.from(context).inflate(R.layout.chat_user_layout,parent,false)
        return UserViewHolder(view)
    }
    // to bind the details we get in the text view in the recycler view
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser= userList[position]
        // to inflate the name of the current user in the recycler view
        holder.textName.text= currentUser.name
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}