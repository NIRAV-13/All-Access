package com.mobile.macs_13

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.mobile.macs_13.model.StudentNotificationData


/**
 * A simple [Fragment] subclass.
 * Use the [StudentHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentHomeFragment : Fragment(R.layout.fragment_student_home) {


    private lateinit var adapter: StudentHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<StudentNotificationData>

/*    val dummy = arrayListOf<StudentNotificationData>(
        StudentNotificationData("Test", "Data"),
        StudentNotificationData("Test 2", "Data")
    )*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNotificationListFromDB()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.student_home_rv)
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