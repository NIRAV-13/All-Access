package com.example.accomodationfeature

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobile.macs_13.R
import com.mobile.macs_13.com.mobile.macs_13.view.student.StudentActivity
import com.mobile.macs_13.com.mobile.macs_13.view.student.StudentProfileActivity
import com.mobile.macs_13.UserFeedbackActivity
import com.mobile.macs_13.controller.DownloadFile
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAccomRequestModel
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.com.mobile.macs_13.view.student.StudentBookAppointmentHome
import com.mobile.macs_13.view.login.Login
import java.util.*

// Class that handles the student accommodation request form
// student will submit this form to apply for accommodation
class StudentAccomodation : AppCompatActivity() {

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var  drawerLayout: DrawerLayout

    private lateinit var edtStudName: TextView
    private lateinit var edtStudEmail: TextView
    private lateinit var edtStudPrefName: TextView
    private lateinit var edtStudImpact: EditText
    private lateinit var edtStudConsent: EditText
    private lateinit var submitBtn: Button
    private lateinit var cancelBtn: Button
    private var studDB = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_accomodation)

        // to set action bar as Accommodation
        supportActionBar?.title = "Accommodation"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout,  R.string.drawer_open, R.string.drawer_closed)
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // TODO: Handle menu item selected
            if(menuItem.itemId == R.id.home_item){
                    val homeIntent = Intent(this, StudentActivity::class.java)
                    finish()
                    startActivity(homeIntent)
                }
            if(menuItem.itemId == R.id.feeback_item){
                val feedbackIntent = Intent(this, UserFeedbackActivity::class.java)
                finish()
                startActivity(feedbackIntent)
            }

            if(menuItem.itemId == R.id.documents_item){
                val documentsIntent = Intent(this, DownloadFile::class.java)
                finish()
                startActivity(documentsIntent)
            }

            if(menuItem.itemId == R.id.accommodation_item){
                val accomodationIntent = Intent(this, StudentAccomodation::class.java)
                finish()
                startActivity(accomodationIntent)
            }

            if(menuItem.itemId == R.id.appointment_item){
                val studentAppointmentHome = Intent(this, StudentBookAppointmentHome::class.java)
                startActivity(studentAppointmentHome)
            }

            if(menuItem.itemId == R.id.profile_item){
                val studentProfile = Intent(this, StudentProfileActivity::class.java)
                startActivity(studentProfile)
            }

            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

        edtStudEmail = findViewById(R.id.stud_email)
        edtStudName = findViewById(R.id.stud_name)
        edtStudPrefName = findViewById(R.id.edt_stud_pref_name)
        edtStudImpact = findViewById(R.id.impact_on_acad)
        edtStudConsent = findViewById(R.id.stud_consent)
        submitBtn = findViewById(R.id.accom_submit_btn)
        cancelBtn = findViewById(R.id.accom_cancel_btn)

        edtStudEmail.text = User.getCurrentUserProfile().email
        edtStudPrefName.text = User.getCurrentUserProfile().name
        Log.d("USER", User.getCurrentUserProfile().uid.toString())

        submitBtn.setOnClickListener {

            val studImpact = edtStudImpact.text.toString().trim()
            val studConsent = edtStudConsent.text.toString().trim()
            val status = "inProgress"

            // user profile database same data class object.
            // accommodation form details.


            var studRequest: StudentAccomRequestModel = StudentAccomRequestModel(
                User.getCurrentUserProfile().uid,
                User.getCurrentUserProfile().name,
                User.getCurrentUserProfile().email,
                User.getCurrentUserProfile().phone,
                User.getCurrentUserProfile().program,
                User.getCurrentUserProfile().course,
                User.getCurrentUserProfile().year,
                User.getCurrentUserProfile().term,
                User.getCurrentUserProfile().imageLink,
                studImpact, studConsent, status, Date()
            )

            if (studImpact.isBlank() || studConsent.isBlank()) {
                Toast.makeText(this, "Please enter all the details!!", Toast.LENGTH_SHORT).show()
            } else {
                // getting current user id
                studDB.collection("Accomodation").document().set(studRequest)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Request submitted successfully", Toast.LENGTH_SHORT)
                            .show()
                        edtStudImpact.text.clear()
                        edtStudConsent.text.clear()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        cancelBtn.setOnClickListener {

            val backIntent = Intent(this@StudentAccomodation, StudentActivity::class.java)
            startActivity(backIntent)
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
        }
        else if(item.itemId == R.id.logout){
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