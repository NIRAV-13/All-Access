package com.mobile.macs_13.controller.accomodation

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mobile.macs_13.com.mobile.macs_13.view.advisor.AdvisorActivity
import com.mobile.macs_13.com.mobile.macs_13.view.advisor.AdvisorAppointments
import com.mobile.macs_13.com.mobile.macs_13.view.advisor.AdvisorProfileActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.AdvisorAccomRequestModel
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.com.mobile.macs_13.view.student.AccomodationListActivity
import com.mobile.macs_13.view.login.Login
import java.io.File


// This class handles the accommodation requests for approval for the students by the advisor
class AdvisorAccomodation : AppCompatActivity() {


    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    private lateinit var studName: TextView
    private lateinit var studEmail: TextView
    private lateinit var studUniv: TextView
    private lateinit var studProgram: TextView
    private lateinit var studCourse: TextView
    private lateinit var studTerm: TextView
    private lateinit var studYear: TextView
    private lateinit var studPhone: TextView
    private lateinit var studDocuments: TextView
    private lateinit var studReason: TextView
    private lateinit var advAccept: Button
    private lateinit var advReject: Button
    private var accomDb = Firebase.firestore
    private lateinit var mAuth: FirebaseAuth
    private var storageinstance = FirebaseStorage.getInstance()

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor_accomodation)

        val advisorAccommodation = this.intent.getSerializableExtra("advisorAccommodation") as AdvisorAccomRequestModel?

        mAuth = FirebaseAuth.getInstance()
        studName = findViewById(R.id.accom_stud_name)
        studUniv = findViewById(R.id.univ_name)
        studProgram = findViewById(R.id.prog_name)
        studCourse = findViewById(R.id.course_name)
        studTerm = findViewById(R.id.stud_term)
        studEmail = findViewById(R.id.student_email)
        studYear = findViewById(R.id.stud_year)
        studPhone = findViewById(R.id.stud_phone)
        studDocuments = findViewById(R.id.stud_doc)
        studReason = findViewById(R.id.stud_reason)
        advAccept = findViewById(R.id.adv_accept)
        advReject = findViewById(R.id.adv_reject)

        studDocuments.setOnClickListener {
            downloadfile("Guide_for_Accomodation.pdf")
        }

        supportActionBar?.title = "Accomodations"
        supportActionBar?.title = "Accomodation"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                val advisorAccommodation = Intent(this, AccomodationListActivity::class.java)
                startActivity(advisorAccommodation)
            }

            menuItem.isChecked = true
            drawerLayout.close()
            true
        }
        // getting data

                studName.text = advisorAccommodation?.name.toString()
                studProgram.text = advisorAccommodation?.program.toString()
                studCourse.text = advisorAccommodation?.course.toString()
                studTerm.text = "2nd Term"
                studYear.text = advisorAccommodation?.year.toString()
                studEmail.text = advisorAccommodation?.email.toString()
                studPhone.text = advisorAccommodation?.phone.toString()
                studReason.text = advisorAccommodation?.impact.toString()
                studDocuments.text = "Click to download documents"


        advAccept.setOnClickListener{
            val dbRef = accomDb.collection("Accomodation").document(advisorAccommodation?.documentId!!)

            dbRef.get().addOnSuccessListener { documents ->

                val status = documents["status"]

                if (status.toString().equals("Accepted") || status.toString().equals("Rejected")) {
                    Toast.makeText(
                        this,
                        "You have already made a decision on this request!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else {
                    val currentUser = User.getCurrentUserProfile()
                    advisorAccommodation?.status = "Accepted"
                    advisorAccommodation?.advisorEmail = currentUser.email
                    advisorAccommodation?.advisorName = currentUser.name
                    advisorAccommodation?.advisorPhone = currentUser.phone
                    advisorAccommodation?.advisorImageLink = currentUser.imageLink


                    dbRef.set(advisorAccommodation!!).addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Student request accepted!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                        .addOnFailureListener{

                            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT)
                                .show()
                        }

                }
            }

        }

        advReject.setOnClickListener{

            val dbRef = accomDb.collection("Accomodation").document(advisorAccommodation?.documentId.toString())
            dbRef.get().addOnSuccessListener { documents ->

                val status = documents["status"] as String

                if (status.equals("Accepted") || status.equals("Rejected")) {
                    Toast.makeText(
                        this@AdvisorAccomodation,
                        "You have already made a decision on this request!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    advisorAccommodation?.status = "Rejected"
                    dbRef.set(advisorAccommodation!!).addOnSuccessListener {
                        Toast.makeText(
                            this@AdvisorAccomodation,
                            "Student request rejected!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                        .addOnFailureListener{
                            Toast.makeText(this@AdvisorAccomodation, "Error!", Toast.LENGTH_SHORT)
                                .show()
                        }


                }
            }
        }

    }



    private fun downloadfile(filename:String){
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val storageref = storageinstance.reference
        println(storageref)
        storageref.child("download_files/$filename").downloadUrl.addOnSuccessListener {
            val uri: Uri = Uri.parse(it.toString())
            val request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .setAllowedOverRoaming(false)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOCUMENTS,
                    File.separator + "$filename"
                )
            downloadManager.enqueue(request)
            println(it.toString())
            Toast.makeText(this, "File downloaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            println(it.toString())
            Toast.makeText(this, "File download failure", Toast.LENGTH_SHORT).show()
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