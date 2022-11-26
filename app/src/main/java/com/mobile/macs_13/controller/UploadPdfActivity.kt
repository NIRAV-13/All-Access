package com.mobile.macs_13.controller

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.StorageReference
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.mobile.macs_13.R

class UploadPdfActivity : AppCompatActivity() {
    lateinit var button: Button
    val pdf: Int = 0
    lateinit var uri: Uri
    lateinit var mStorage: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_pdf)
        mStorage = FirebaseStorage.getInstance().getReference("uploads")
        button = findViewById(R.id.uploadButton)
        button.setOnClickListener { view: View? ->
            val intent = Intent()
            intent.type= "application/pdf"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), pdf)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uriTxt = findViewById<TextView>(R.id.uriTxt)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == pdf) {
                uri = data!!.data!!
                uriTxt.text = uri.toString()
                upload()

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun upload() {
        var mRefrence = mStorage.child(uri.lastPathSegment.toString())
        try {
            mRefrence.putFile(uri).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                var url = taskSnapshot!!.uploadSessionUri
                val dwnTxt = findViewById<TextView>(R.id.dwnTxt)
                dwnTxt.text = url.toString()
                Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }


    }
}