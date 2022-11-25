package com.mobile.macs_13


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
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mobile.macs_13.controller.DownloadFile
import com.mobile.macs_13.controller.authentication.Login
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile


class StudentActivity : AppCompatActivity() {
    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var  drawerLayout: DrawerLayout
    private lateinit var loginAuth : FirebaseAuth


    private lateinit var adapter: StudentHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<StudentNotificationData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        getNotificationListFromDB()
        val layoutManager = LinearLayoutManager(this.baseContext)
        recyclerView = findViewById (R.id.student_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = StudentHomeAdapter(notificationList)
        recyclerView.adapter = adapter

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout,  R.string.drawer_open, R.string.drawer_closed)
        drawerLayout.addDrawerListener(mActionBarDrawerToggle)
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
        mActionBarDrawerToggle.syncState()

        loginAuth = FirebaseAuth.getInstance()

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
        recyclerView = findViewById (R.id.student_home_rv)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = StudentHomeAdapter(notificationList)
        recyclerView.adapter = adapter


    }


    private fun getNotificationListFromDB() {

        notificationList = arrayListOf<StudentNotificationData>()
        val db = FirebaseFirestore.getInstance()
        db.collection("StudentHomeNotifications").orderBy("timestamp")
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

                        if (document.type == DocumentChange.Type.ADDED || document.type == DocumentChange.Type.MODIFIED /*|| document.type == DocumentChange.Type.REMOVED*/) {
                            notificationList.add(document.document.toObject(StudentNotificationData::class.java))
                        }
                    }

                    adapter.notifyDataSetChanged()
                }

            })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) { // use android.R.id
            drawerLayout.openDrawer(Gravity.LEFT);
            return true
        }
        else if(item.itemId == R.id.logout){
            loginAuth.signOut()
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