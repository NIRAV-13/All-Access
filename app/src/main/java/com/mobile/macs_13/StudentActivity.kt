package com.mobile.macs_13


import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class StudentActivity : AppCompatActivity() {
    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var  drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout,  R.string.drawer_open, R.string.drawer_closed)
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // TODO: Handle menu item selected
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) { // use android.R.id
            drawerLayout.openDrawer(Gravity.LEFT);
            return true
        }
        return super.onOptionsItemSelected(item);
    }
}