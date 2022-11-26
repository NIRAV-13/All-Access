package com.mobile.macs_13.controller

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.FileOperation.UploadFile

import java.io.File

class DownloadFile : AppCompatActivity() {
    lateinit var guidebutton: Button
    lateinit var accomodationbutton:Button
    private var storageinstance = FirebaseStorage.getInstance()
    lateinit var  uploadbutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_file)
        guidebutton = findViewById(R.id.button2)
        accomodationbutton = findViewById(R.id.button3)
        uploadbutton = findViewById(R.id.button5)
        guidebutton.setOnClickListener {
                downloadfile("Guide_for_Accomodation.pdf")
        }
        accomodationbutton.setOnClickListener{
            downloadfile("Accomodation_Request.pdf")
        }
        val staticText1 = findViewById<TextView>(R.id.info_text) as TextView
        staticText1.text =
            "The step-by-step booking approach for the assistance needed from the advisor"
        val staticText2 = findViewById<TextView>(R.id.info_text2) as TextView
        staticText2.text =
            "You can submit an accommodation request by filling out this form and uploading it as a file. Your completed form can be reviewed by the advisor. ";


        uploadbutton.setOnClickListener {
            val uploadFileIntent = Intent(this, UploadFile::class.java)
            startActivity(uploadFileIntent)
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
            Toast.makeText(this, "File downloaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            println(it.toString())
            Toast.makeText(this, "File download failure", Toast.LENGTH_SHORT).show()
        }
    }

}