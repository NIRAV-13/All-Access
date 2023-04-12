package com.mobile.macs_13.com.mobile.macs_13.view.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.mobile.macs_13.R
import com.mobile.macs_13.model.chat.ChatMessageModel

class ChatMessageAdapter(val context: Context, val messageList: ArrayList<ChatMessageModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentMessage = itemView.findViewById<TextView>(R.id.sent_message)
    }

    class ReceiveMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.received_message)
    }

    val SENT_MESSAGE = 2
    val RECEIVE_MESSAGE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1){
            // receive message
            val view: View= LayoutInflater.from(context).inflate(R.layout.message_received,parent,false)
            return ReceiveMessageViewHolder(view)
        }else{
            // send message
            val view: View= LayoutInflater.from(context).inflate(R.layout.message_sent,parent,false)
            return SentMessageViewHolder(view)

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass== SentMessageViewHolder::class.java){
                // implement the logic for sent messages
                // typecasting viewholder to sent message viewholder
                val viewHolder= holder as SentMessageViewHolder
                holder.sentMessage.text = currentMessage.message

            }
        else{
            // implement logic for received messages

                // typecasting viewholder to received message viewholder
                val viewHolder= holder as ReceiveMessageViewHolder
                holder.receivedMessage.text = currentMessage.message
            }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendUserID)){
            return SENT_MESSAGE
        }else{
            return RECEIVE_MESSAGE
        }
    }
    override fun getItemCount(): Int {

        return messageList.size
    }
}