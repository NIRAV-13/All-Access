package com.mobile.macs_13.controller.accomodation

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.AdvisorAccomRequestModel
import com.mobile.macs_13.model.SlotDetail
import com.mobile.macs_13.model.StudentAccomRequestModel
import java.io.File

class AdvisorAccomodation : AppCompatActivity() {

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
//            downloadfile("Guide_for_Accomodation.pdf")
        }
        // getting data

                studName.text = advisorAccommodation?.studentName.toString()
                studProgram.text = advisorAccommodation?.studentProgram.toString()
                studCourse.text = advisorAccommodation?.studentCourse.toString()
                studTerm.text = advisorAccommodation?.studentTerm.toString()
                studYear.text = advisorAccommodation?.studentYear.toString()
                studEmail.text = advisorAccommodation?.studentEmail.toString()
                studPhone.text = advisorAccommodation?.studentPhone.toString()
                studReason.text = advisorAccommodation?.studentImpact.toString()
                studDocuments.text = advisorAccommodation?.studentDocs.toString()
//                val stud_Docs = it.data?.get("docs")?.toString()



//            val reqObject: StudentAccomRequestModel = StudentAccomRequestModel()

//            val sendWithAdvisorDetails : AdvisorAccomRequestModel = AdvisorAccomRequestModel(reqObject.uid,
//                reqObject.name, reqObject.email, reqObject.phone, reqObject.program, reqObject.course,
//                reqObject.year, reqObject.term, null, reqObject.imageLink, reqObject.impact,
//                reqObject.consent, reqObject.status, reqObject.timeStamp, User.getCurrentUserProfile().name, User.getCurrentUserProfile().email, User.getCurrentUserProfile().imageLink,
//                User.getCurrentUserProfile().phone)



        Log.d("XYZ","$advisorAccommodation")

        advAccept.setOnClickListener{
            val dbRef = accomDb.collection("Accomodation").document(advisorAccommodation?.documentId!!)

            dbRef.get().addOnSuccessListener { documents ->

                Log.d("DATA","${documents.data}")
                val status = documents["status"] as String

                if (status.equals("Accepted") || status.equals("Rejected")) {
                    Toast.makeText(
                        this@AdvisorAccomodation,
                        "You have already made a decision on this request!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else {

                    advisorAccommodation?.studentStatus = "Accepted"
                    dbRef.set(advisorAccommodation!!).addOnSuccessListener {
                        Toast.makeText(
                            this@AdvisorAccomodation,
                            "Student request accepted!",
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
                    advisorAccommodation?.studentStatus = "Rejected"
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
}