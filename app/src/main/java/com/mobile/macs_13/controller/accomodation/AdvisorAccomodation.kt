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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mobile.macs_13.AdvisorAppointments
import com.mobile.macs_13.AdvisorProfileActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile
import com.mobile.macs_13.view.login.Login
import java.io.File

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
    private lateinit var advComments: TextView
    private lateinit var accomDbRef : DatabaseReference
    private var accomDb = Firebase.firestore
    private lateinit var studDb : DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var storageinstance = FirebaseStorage.getInstance()
//    val currentUserID = mAuth.currentUser

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advisor_accomodation)

        mAuth = FirebaseAuth.getInstance()
        studName = findViewById(R.id.accom_stud_name)
        studUniv = findViewById(R.id.univ_name)
        studProgram = findViewById(R.id.prog_name)
        studCourse = findViewById(R.id.course_name)
        studTerm = findViewById(R.id.stud_term)
        studEmail = findViewById(R.id.stud_email)
        studYear = findViewById(R.id.stud_year)
        studPhone = findViewById(R.id.stud_phone)
        studDocuments = findViewById(R.id.stud_doc)
        studReason = findViewById(R.id.stud_reason)
        advAccept = findViewById(R.id.adv_accept)
        advReject = findViewById(R.id.adv_reject)
        studDocuments.setOnClickListener {
            downloadfile("Guide_for_Accomodation.pdf")
        }
        supportActionBar?.title = "Appointments"
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
        // getting data
        val studDbRef = accomDb.collection("Accomodation").document("muHaLbJ3Oc2nCCdDQmdM")
        studDbRef.get().addOnSuccessListener{
            if(it!= null){
                val stud_course = it.data?.get("course")?.toString()
                val stud_program = it.data?.get("program")?.toString()
                val stud_phone = it.data?.get("phone")?.toString()
                val stud_email = it.data?.get("email")?.toString()
                val stud_year = it.data?.get("year")?.toString()
                val stud_term = it.data?.get("term")?.toString()
                val stud_Reason = it.data?.get("impact")?.toString()
                val stud_name = it.data?.get("name")?.toString()
//                val stud_Docs = it.data?.get("docs")?.toString()
                studReason.text = stud_Reason
                studPhone.text = stud_phone
                studEmail.text = stud_email
                studTerm.text = stud_term
                studYear.text = stud_year
                studProgram.text = stud_program
                studCourse.text = stud_course
                studName.text = stud_name
//                studDocuments.text = stud_Docs

            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Data retrieval failed!! ", Toast.LENGTH_SHORT).show()
            }

        val advisorAcceptMap = hashMapOf(

            "status" to "Accepted",
            "TimeStamp" to FieldValue.serverTimestamp()
        )
        val advisorRejectMap = hashMapOf(

            "status" to "Rejected",
            "TimeStamp" to FieldValue.serverTimestamp()
        )


        advAccept.setOnClickListener{

            val dbRef = accomDb.collection("Accomodation").document("muHaLbJ3Oc2nCCdDQmdM")
            dbRef.get().addOnSuccessListener { documents ->
                if (documents?.data?.get("status") == "Accepted" || documents?.data?.get("status") == "Rejected") {
                    Toast.makeText(
                        this@AdvisorAccomodation,
                        "You have already made a decision on this request!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val accomDbRef =
                        accomDb.collection("Accomodation").document("muHaLbJ3Oc2nCCdDQmdM")
                    accomDbRef.update(advisorAcceptMap).addOnSuccessListener {

                        Toast.makeText(
                            this@AdvisorAccomodation,
                            "Student request accepted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                        .addOnFailureListener {
                            Toast.makeText(this@AdvisorAccomodation, "Error!", Toast.LENGTH_SHORT)
                                .show()
                        }

                }
            }
        }

        advReject.setOnClickListener{

            val dbRef = accomDb.collection("Accomodation").document("muHaLbJ3Oc2nCCdDQmdM")
            dbRef.get().addOnSuccessListener { documents ->
                if (documents?.data?.get("status") == "Accepted" || documents?.data?.get("status") == "Rejected") {
                    Toast.makeText(
                        this@AdvisorAccomodation,
                        "You have already made a decision on this request!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val accomDbRef =
                        accomDb.collection("Accomodation").document("muHaLbJ3Oc2nCCdDQmdM")
                    accomDbRef.update(advisorRejectMap).addOnSuccessListener {

                        Toast.makeText(
                            this@AdvisorAccomodation,
                            "Student request rejected!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                        .addOnFailureListener {
                            Toast.makeText(
                                this@AdvisorAccomodation,
                                "Error!",
                                Toast.LENGTH_SHORT
                            ).show()
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