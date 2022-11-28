package com.mobile.macs_13.view

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
import com.mobile.macs_13.InstructorProfileActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.com.mobile.macs_13.model.StudentWithAccommodationAdapter
import com.mobile.macs_13.controller.InstructorController
import com.mobile.macs_13.controller.StudentController
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.StudentAppointmentListAdapter
import com.mobile.macs_13.model.StudentWithAccommodationList
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.login.Login

class InstructorHomePageActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentWithAccommodationAdapter: StudentWithAccommodationAdapter

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    private var instructorController: InstructorController = InstructorController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_home_page)

        recyclerView = findViewById(R.id.instructorHomePageRecycler)
        recyclerView.setHasFixedSize(true)
        studentWithAccommodationAdapter =
            StudentWithAccommodationAdapter(StudentWithAccommodationList.getStudentsWithAccommodation())
        recyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView.adapter = studentWithAccommodationAdapter


        val user = User.getCurrentUserProfile()

        instructorController.fetchCurrentStudents(
            user.term.toString(),
            user.course.toString()
        ) { success ->

            if (success) {
                studentWithAccommodationAdapter.notifyDataSetChanged()
            } else {
                Log.d("data", "something went wrong.")
            }
        }

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
            if (menuItem.itemId == R.id.home_item) {
                val homeIntent = Intent(this, InstructorHomePageActivity::class.java)
                finish()
                startActivity(homeIntent)
            }
            if (menuItem.itemId == R.id.profile_item) {
                val advisorProfile = Intent(this, InstructorProfileActivity::class.java)
                startActivity(advisorProfile)
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