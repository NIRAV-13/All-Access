package com.mobile.macs_13.com.mobile.macs_13.view.student

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accomodationfeature.StudentAccomodation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.mobile.macs_13.R
import com.mobile.macs_13.UserFeedbackActivity
import com.mobile.macs_13.controller.DownloadFile
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAppointmentListAdapter
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.login.Login

// Activity class for students to book appointment with advisor.
class StudentBookAppointmentHome : AppCompatActivity() {

    // Declaring required private attributes.
    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var addAppointmentButton: FloatingActionButton
    private var studentController: StudentController = StudentController()
    private lateinit var studentAppointmentListAdapter: StudentAppointmentListAdapter
    private val student = User.getCurrentUserProfile()

    // Activity onCreate methods
    override fun onCreate(savedInstanceState: Bundle?) {

        // Setting content page.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_appointment_list)

        // Title for the page.
        supportActionBar?.title = "Appointments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        // Setting up recycler view.
        recyclerView = findViewById(R.id.studentAppointmentListRecyclerView)
        recyclerView.setHasFixedSize(true)
        studentAppointmentListAdapter = StudentAppointmentListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = studentAppointmentListAdapter

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

            if(menuItem.itemId == R.id.home_item){
                val homeIntent = Intent(this, StudentActivity::class.java)
                finish()
                startActivity(homeIntent)
            }

            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

        val user = User.getCurrentUserProfile()

        // Fetching appointments of the current student.
        studentController.fetchAppointments(student.email.toString()) { success ->

            if (success) {
                studentAppointmentListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(
                    this,
                    "Something went wrong. Please try again later.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        // On click event for add appointment button.
        addAppointmentButton = findViewById(R.id.addAppointmentButton)
        addAppointmentButton.setOnClickListener {
            val advisorListIntent = Intent(this, StudentAppointmentAdvisorListActivity::class.java)
            startActivity(advisorListIntent)
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