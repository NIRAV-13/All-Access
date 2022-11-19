package com.mobile.macs_13.controller

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mobile.macs_13.R
import java.io.File

class Uploadpdf : AppCompatActivity() {
    lateinit var button:Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploadpdf)
        var storageinstance = FirebaseStorage.getInstance()
        var permission = 1
        var requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            permission = if(it){
                1
            }else{
                0
            }
        }
        button = findViewById(R.id.button2)
        button.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if(permission==1){
                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val storageref = storageinstance.getReference()
                var folder:File
                storageref.child("temp/sample.pdf").downloadUrl.addOnSuccessListener {
                    val uri: Uri = Uri.parse(it.toString())
                    val request = DownloadManager.Request(uri)
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                        .setMimeType("application/pdf").setAllowedOverRoaming(false)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,File.separator+"sample"+".pdf")
                    downloadManager.enqueue(request)

                    println(it.toString())
                    Toast.makeText(this,"File downloaded",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    println(it.toString())
                    Toast.makeText(this,"File download failure",Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}