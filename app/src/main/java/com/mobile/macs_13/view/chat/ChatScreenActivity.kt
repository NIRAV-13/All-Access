package com.mobile.macs_13.view.chat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.macs_13.R
import com.mobile.macs_13.com.mobile.macs_13.view.chat.ChatMessageAdapter
import com.mobile.macs_13.model.chat.ChatMessageModel

class ChatScreenActivity : AppCompatActivity() {

    private lateinit var chatMessageRecyclerView: RecyclerView
    private lateinit var sendMessageBox: EditText
    private lateinit var sendMsgBtn: ImageView
    private lateinit var chatMessageAdapter: ChatMessageAdapter
    private lateinit var chatMessageList : ArrayList<ChatMessageModel>
    private lateinit var msgDbRef: DatabaseReference

    // for creating unique room for the sender and receiver
    var receiverMessageRoom: String? = null
    var senderMessageRoom: String?= null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        //fetching name and uid from the user activity
        val name = intent.getStringExtra("name")
        val receiverUID = intent.getStringExtra("uid")
        val senderUID = FirebaseAuth.getInstance().currentUser?.uid
        msgDbRef = FirebaseDatabase.getInstance().getReference()
        senderMessageRoom = receiverUID + senderUID
        receiverMessageRoom = senderUID + receiverUID

        // to show the name of the user in the action bar
        supportActionBar?.title = name

        chatMessageRecyclerView = findViewById(R.id.chatMessageRecyclerView)
        sendMessageBox = findViewById(R.id.messageBox)
        sendMsgBtn = findViewById(R.id.sendMsgBtn)
        chatMessageList = ArrayList()
        chatMessageAdapter = ChatMessageAdapter(this,chatMessageList)

        // setting layout manager and chat message adapter to the recycler view
        chatMessageRecyclerView.layoutManager = LinearLayoutManager(this)
        chatMessageRecyclerView.adapter = chatMessageAdapter

        // to add the data to the recycler view for the chat messages

        msgDbRef.child("chats").child(senderMessageRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                // to clear the previous value stored in the database
                chatMessageList.clear()

                for (postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(ChatMessageModel::class.java)
                    chatMessageList.add(message!!)
                }
                chatMessageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        // submitting the chat message to database
        sendMsgBtn.setOnClickListener{
            val chatMessage = sendMessageBox.text.toString()
            if(chatMessage.isBlank()){
                Toast.makeText(this@ChatScreenActivity,"Please type some message!!", Toast.LENGTH_SHORT).show()
            } else{
                val messageObject = ChatMessageModel(chatMessage,senderUID)
                msgDbRef.child("chats").child(senderMessageRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                    msgDbRef.child("chats").child(receiverMessageRoom!!).child("messages").push().setValue(messageObject)
                }
            }
            sendMessageBox.setText("")

        }

    }
}