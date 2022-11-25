package com.mobile.macs_13.controller

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.storage.FirebaseStorage
import com.mobile.macs_13.R
import java.io.File

class DownloadFile : AppCompatActivity() {
    lateinit var guidebutton: Button
    lateinit var accomodationbutton:Button
    private var storageinstance = FirebaseStorage.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_file)
        guidebutton = findViewById(R.id.button2)
        accomodationbutton = findViewById(R.id.button3)
        guidebutton.setOnClickListener {
                downloadfile("Guide_for_Accomodation.docx")
        }
        accomodationbutton.setOnClickListener{
            downloadfile("Accomodation_Request.docx")
        }


        val staticText1 = findViewById<TextView>(R.id.info_text) as TextView
        staticText1.text =
            "The Booking for the accessibility form is given in pdf file step by step. Using this a successful booking for the advisor is done and further timing can be scheduled"
        val staticText2 = findViewById<TextView>(R.id.info_text2) as TextView
        staticText2.text =
            "The Accomodation form can be downloaded and submitted in the Upload Section by clicking on the option of Accomodation ";
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