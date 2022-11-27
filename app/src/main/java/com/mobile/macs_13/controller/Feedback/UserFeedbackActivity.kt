package com.mobile.macs_13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.accomodationfeature.StudentAccomodation
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.macs_13.controller.DownloadFile
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.FeedbackModel
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.StudentBookAppointmentHome
import com.mobile.macs_13.view.login.Login


class UserFeedbackActivity : AppCompatActivity() {

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var  drawerLayout: DrawerLayout
    private lateinit var optiondropdown: Spinner
    private lateinit var meeting:String
    private lateinit var submit: Button
    private lateinit var advisor:String
    private lateinit var discussion: String
    private lateinit var duration:String
    private lateinit var subj:String
    private lateinit var suggestion:String
    private var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_feedback)

        supportActionBar?.title = "Feedback"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout,  R.string.drawer_open, R.string.drawer_closed)
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // TODO: Handle menu item selected
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

        optiondropdown=findViewById(R.id.spinner);
        var adapter = ArrayAdapter.createFromResource(this, R.array.options,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        optiondropdown.setAdapter(adapter);
        submit = findViewById(R.id.button4)

        submit.setOnClickListener {
            advisor = findViewById<TextView>(R.id.edt_advisor).text.toString()
            discussion = findViewById<TextView>(R.id.edt_discussion).text.toString()
            duration = findViewById<TextView>(R.id.edt_duration).text.toString()
            subj = findViewById<TextView>(R.id.edt_subj).text.toString()
            suggestion = findViewById<TextView>(R.id.edt_suggestion).text.toString()
            meeting  = optiondropdown.selectedItem.toString()
            saveData()
        }
    }
    private fun saveData(){
        val data = FeedbackModel(advisor, discussion, duration, subj, suggestion, meeting)
        db.collection("user_feedback").document().set(data).addOnSuccessListener {
            Toast.makeText(this, "Feedback Sent", Toast.LENGTH_LONG).show()
            val intent = Intent(this, StudentActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(this, "Error occurred",Toast.LENGTH_SHORT).show()
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