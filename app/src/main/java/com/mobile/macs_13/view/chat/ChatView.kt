package com.mobile.macs_13.view.chat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.macs_13.R


import com.mobile.macs_13.controller.authentication.Login
import com.mobile.macs_13.controller.chat.UserAdapter
import com.mobile.macs_13.model.chat.UserModel


class ChatView : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var userAdapter: UserAdapter
    private lateinit var userDbRef: DatabaseReference
    private lateinit var loginAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_view)

        loginAuth = FirebaseAuth.getInstance()
        userDbRef= FirebaseDatabase.getInstance().getReference()

        userList= ArrayList()
        userAdapter= UserAdapter(this,userList)
        userRecyclerView= findViewById(R.id.chatUserRecyclerView)

        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.adapter= userAdapter


        userDbRef.child("users").addValueEventListener(object :ValueEventListener {


            // Using snapshot to get the data from the database
            // it gets the snapshot of the schema of the database
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(UserModel::class.java)

                    if(loginAuth.currentUser?.uid!=currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            loginAuth.signOut()
            val logoutIntent = Intent(this@ChatView, Login::class.java)
            finish()
            startActivity(logoutIntent)
            return true
        }
        return true
    }
}
