package com.mobile.macs_13.com.mobile.macs_13.view.instructor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.login.Login

/**
 * Instructor Profile Activity - Shows the Profile Information for the Instructor
 * @author Ankush Mudgal
 */
class InstructorProfileActivity : AppCompatActivity() {

    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_profile)

        val advisorImage = findViewById<ImageView>(R.id.instructor_profile_image)
        val advisorName = findViewById<TextView>(R.id.instructor_profile_name)
        val advisorEmail = findViewById<TextView>(R.id.instructor_profile_email)
        val advisorPhone = findViewById<TextView>(R.id.instructor_profile_phone)
        val advisorInfo = findViewById<TextView>(R.id.instructor_profile_text)

        // fetch the Image to be displayed
        Glide.with(this.baseContext)
            .load(User.getCurrentUserProfile().imageLink)
            .centerCrop().placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile)
            .into(advisorImage);

        advisorName.text = "NAME - " + User.getCurrentUserProfile().name
        advisorEmail.text = "EMAIL - " + User.getCurrentUserProfile().email
        advisorPhone.text = "PHONE - " + User.getCurrentUserProfile().phone
        advisorInfo.text = "INFO - " + User.getCurrentUserProfile().course
        Log.d("INS", User.getCurrentUserProfile().toString())

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