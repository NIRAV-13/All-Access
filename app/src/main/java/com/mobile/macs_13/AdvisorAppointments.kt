package com.mobile.macs_13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.mobile.macs_13.controller.accomodation.AdvisorAccomodation
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.AppointmentDetails
import com.mobile.macs_13.model.StudentAccomRequestModel
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.login.Login

class AdvisorAppointments : AppCompatActivity() {

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

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

        supportActionBar?.title = "Appointments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mActionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_closed)
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // TODO: Handle menu item selected
            if(menuItem.itemId == R.id.home_item){
                val homeIntent = Intent(this, AdvisorActivity::class.java)
                finish()
                startActivity(homeIntent)
            }

            if (menuItem.itemId == R.id.profile_item) {
                val advisorProfile = Intent(this, AdvisorProfileActivity::class.java)
                startActivity(advisorProfile)
            }

            if (menuItem.itemId == R.id.appointment_item) {
                val advisorAppt = Intent(this, AdvisorAppointments::class.java)
                startActivity(advisorAppt)
            }

            if (menuItem.itemId == R.id.accommodation_item) {
                val advisorAccommodation = Intent(this, AdvisorAccomodation::class.java)
                startActivity(advisorAccommodation)
            }

            menuItem.isChecked = true
            drawerLayout.close()
            true
        }


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

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mActionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) { // use android.R.id
            drawerLayout.openDrawer(Gravity.LEFT);
            return true
        } else if (item.itemId == R.id.logout) {
            FirebaseRefSingleton.getFirebaseAuth().signOut()
            val logoutIntent = Intent(this, Login::class.java)
            User.setCurrentUserProfile(UserProfile())
            finish()
            startActivity(logoutIntent)
            return true
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }
}