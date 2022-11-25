package com.mobile.macs_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.mobile.macs_13.model.StudentNotificationData

class StudentActivity : AppCompatActivity() {


    private lateinit var adapter: StudentHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<StudentNotificationData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        getNotificationListFromDB()
        val layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView = findViewById (R.id.student_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = StudentHomeAdapter(notificationList)
        recyclerView.adapter = adapter


    }


    private fun getNotificationListFromDB() {

        notificationList = arrayListOf<StudentNotificationData>()
        val db = FirebaseFirestore.getInstance()
        db.collection("StudentHomeNotifications").orderBy("timestamp")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Log.d(
                            "Error",
                            "Some Error in Connection to FireStore ${error.message.toString()}"
                        )
                        return
                    }

                    for (document: DocumentChange in value?.documentChanges!!) {

                        if (document.type == DocumentChange.Type.ADDED || document.type == DocumentChange.Type.MODIFIED /*|| document.type == DocumentChange.Type.REMOVED*/) {
                            notificationList.add(document.document.toObject(StudentNotificationData::class.java))
                        }
                    }

                    adapter.notifyDataSetChanged()
                }

            })

    }
}