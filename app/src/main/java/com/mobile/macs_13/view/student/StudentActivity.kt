package com.mobile.macs_13.com.mobile.macs_13.view.student


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.mobile.macs_13.model.StudentNotificationData
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.accomodationfeature.StudentAccomodation
import com.google.android.material.navigation.NavigationView
import com.mobile.macs_13.R
import com.mobile.macs_13.UserFeedbackActivity
import com.mobile.macs_13.controller.DownloadFile
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.view.login.Login
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile


/**
 * Student Home Page - Loads the Student Flow's homepage, and populates it with notifications.
 * @author Ankush Mudgal
 */
class StudentActivity : AppCompatActivity() {

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: StudentHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<StudentNotificationData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        //Initiate the fetch for Data for the Adapter
        getNotificationListFromDB()
        val layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView = findViewById(R.id.student_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = StudentHomeAdapter(notificationList)
        recyclerView.adapter = adapter

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val studentName = findViewById<TextView>(R.id.student_name)
        studentName.text = "Hello! ${User.getCurrentUserProfile().name}"

        drawerLayout = findViewById(R.id.drawerLayout)
        mActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.drawer_open,
            R.string.drawer_closed
        )
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // TODO: Handle menu item selected
            if (menuItem.itemId == R.id.home_item) {
                val homeIntent = Intent(this, StudentActivity::class.java)
                finish()
                startActivity(homeIntent)
            }
            if (menuItem.itemId == R.id.feeback_item) {
                val feedbackIntent = Intent(this, UserFeedbackActivity::class.java)
                finish()
                startActivity(feedbackIntent)
            }

            if (menuItem.itemId == R.id.documents_item) {
                val documentsIntent = Intent(this, DownloadFile::class.java)
                finish()
                startActivity(documentsIntent)
            }

            if (menuItem.itemId == R.id.accommodation_item) {
                val accommodationIntent = Intent(this, StudentAccomodation::class.java)
                finish()
                startActivity(accommodationIntent)
            }

            if (menuItem.itemId == R.id.appointment_item) {
                val studentAppointmentHome = Intent(this, StudentBookAppointmentHome::class.java)
                startActivity(studentAppointmentHome)
            }

            if (menuItem.itemId == R.id.profile_item) {
                val studentProfile = Intent(this, StudentProfileActivity::class.java)
                startActivity(studentProfile)
            }

            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mActionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mActionBarDrawerToggle.onConfigurationChanged(newConfig)

        getNotificationListFromDB()
        val layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView = findViewById(R.id.student_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = StudentHomeAdapter(notificationList)
        recyclerView.adapter = adapter


    }

    // Method to fetch the Notifications list From Firestore DB
    private fun getNotificationListFromDB() {

        notificationList = arrayListOf()
        //get the notifications in the descending order
        FirebaseRefSingleton.getFirebaseDBInstance().collection("StudentHomeNotifications").orderBy("timestamp", Query.Direction.DESCENDING)
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

                        // Add New or Modified Notifications on the View
                        if (document.type == DocumentChange.Type.ADDED || document.type == DocumentChange.Type.MODIFIED /*|| document.type == DocumentChange.Type.REMOVED*/) {
                            // Add the notifications to the list
                            notificationList.add(document.document.toObject(StudentNotificationData::class.java))
                        }
                    }

                    adapter.notifyDataSetChanged()
                }

            })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { // use android.R.id
            drawerLayout.openDrawer(Gravity.LEFT)
            return true
        } else if (item.itemId == R.id.logout) {
            FirebaseRefSingleton.getFirebaseAuth().signOut()
            val logoutIntent = Intent(this, Login::class.java)
            User.setCurrentUserProfile(UserProfile())
            finish()
            startActivity(logoutIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

}