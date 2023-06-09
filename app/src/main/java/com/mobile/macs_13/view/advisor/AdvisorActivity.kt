package com.mobile.macs_13.com.mobile.macs_13.view.advisor


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.AdvisorController
import com.mobile.macs_13.controller.accomodation.AdvisorAccomodation
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.view.login.Login
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAccomRequestModel
import com.mobile.macs_13.model.UserProfile

/**
 * Advisor Home Page - Loads the Advisor Flow's homepage, and populates it with latest Accommodation requests.
 * @author Ankush Mudgal
 */
class AdvisorActivity : AppCompatActivity() {

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    private lateinit var adapter: AdvisorHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var accomRequestList: ArrayList<StudentAccomRequestModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor)
        accomRequestList = arrayListOf<StudentAccomRequestModel>()

        // Call method in the controller to pull data from the DB
        AdvisorController.getRequestListFromDB { list ->
            if (!list.isEmpty()) {
                val layoutManager = LinearLayoutManager(this.baseContext)
                recyclerView = findViewById(R.id.advisor_home_rv)
                recyclerView.layoutManager = layoutManager
                recyclerView.setHasFixedSize(true)
                Log.d("LIST2", list.toString())
                // Push the data to Adapter for the Recycler View
                adapter = AdvisorHomeAdapter(list)
                recyclerView.adapter = adapter
            }
        }

        val advisorName = findViewById<TextView>(R.id.advisor_name)
        advisorName.text = "Hello! ${User.getCurrentUserProfile().name}"

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

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