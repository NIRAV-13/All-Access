package com.mobile.macs_13.com.mobile.macs_13.view.student

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accomodationfeature.StudentAccomodation
import com.google.android.material.navigation.NavigationView
import com.mobile.macs_13.R
import com.mobile.macs_13.UserFeedbackActivity
import com.mobile.macs_13.controller.DownloadFile
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.AdvisorList
import com.mobile.macs_13.model.AdvisorListAdapter
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.login.Login

class StudentAppointmentAdvisorListActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private var studentController: StudentController = StudentController()
    private lateinit var advisorListAdapter: AdvisorListAdapter

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_appointment_advisor_list)

        recyclerView = findViewById(R.id.advisorListRecyclerView)
        recyclerView.setHasFixedSize(true)
        advisorListAdapter = AdvisorListAdapter(AdvisorList.getAdvisors())
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = advisorListAdapter

        studentController.fetchAdvisorList { success ->
            advisorListAdapter.notifyDataSetChanged()
            Log.d("data", "${AdvisorList.getAdvisors()}")
        }

        supportActionBar?.title = "Appointments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mActionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_closed)
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // TODO: Handle menu item selected
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
                val accomodationIntent = Intent(this, StudentAccomodation::class.java)
                finish()
                startActivity(accomodationIntent)
            }

            if (menuItem.itemId == R.id.appointment_item) {
                val studentAppointmentHome = Intent(this, StudentBookAppointmentHome::class.java)
                startActivity(studentAppointmentHome)
            }

            if (menuItem.itemId == R.id.profile_item) {
                val studentProfile = Intent(this, StudentProfileActivity::class.java)
                startActivity(studentProfile)
            }

            if (menuItem.itemId == R.id.home_item) {
                val homeIntent = Intent(this, StudentActivity::class.java)
                finish()
                startActivity(homeIntent)
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