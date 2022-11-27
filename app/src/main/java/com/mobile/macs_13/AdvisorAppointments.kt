package com.mobile.macs_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.mobile.macs_13.model.AppointmentDetails
import com.mobile.macs_13.model.StudentAccomRequestModel

class AdvisorAppointments : AppCompatActivity() {


    private lateinit var adapter: AdvisorAppointmentsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var appointmentsList: ArrayList<AppointmentDetails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor_appointments)
        getRequestListFromDB()

        val layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView = findViewById(R.id.advisor_appointment_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AdvisorAppointmentsAdapter(appointmentsList)
        recyclerView.adapter = adapter

    }

    private fun getRequestListFromDB() {
        appointmentsList = arrayListOf<AppointmentDetails>()
        val db = FirebaseFirestore.getInstance()

        db.collection("AppointmentDetails")
            //todo Add logic to show only upcoming appointments & appoints with status true
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
                            appointmentsList.add(document.document.toObject(AppointmentDetails::class.java))
                        }
                    }

                    Log.d("LIST", appointmentsList.size.toString())
                    adapter.notifyDataSetChanged()
                }

            })

    }
}