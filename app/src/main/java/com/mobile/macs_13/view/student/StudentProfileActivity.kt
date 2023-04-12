package com.mobile.macs_13.com.mobile.macs_13.view.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.accomodationfeature.StudentAccomodation
import com.google.android.material.navigation.NavigationView
import com.mobile.macs_13.R
import com.mobile.macs_13.UserFeedbackActivity
import com.mobile.macs_13.controller.DownloadFile
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.login.Login

/**
 * Student Profile Activity - Shows the Profile Information for the Student
 * @author Ankkush Mudgal
 */
class StudentProfileActivity : AppCompatActivity() {

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val studentImage = findViewById<ImageView>(R.id.student_profile_image)
        val studentName = findViewById<TextView>(R.id.student_profile_name)
        val studentEmail = findViewById<TextView>(R.id.student_profile_email)
        val studentPhone = findViewById<TextView>(R.id.student_profile_phone)
        val studentProgram = findViewById<TextView>(R.id.student_profile_program)
        val studentTerm = findViewById<TextView>(R.id.student_profile_term)

        // fetch the Image to be displayed
        Glide.with(this.baseContext)
            .load(User.getCurrentUserProfile().imageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(studentImage);

        //Populate the information on the CardView
        studentName.text = "NAME - " + User.getCurrentUserProfile().name
        studentEmail.text = "EMAIL - " + User.getCurrentUserProfile().email
        studentPhone.text = "PHONE - " + User.getCurrentUserProfile().phone
        studentProgram.text = "PROGRAM - " + User.getCurrentUserProfile().program
        studentTerm.text = "TERM - " + User.getCurrentUserProfile().term


        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mActionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_closed)
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()


        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            if(menuItem.itemId == R.id.home_item){
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

            menuItem.isChecked = true
            drawerLayout.close()
            true
        }
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

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mActionBarDrawerToggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }
}