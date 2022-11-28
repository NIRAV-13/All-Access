package com.mobile.macs_13.com.mobile.macs_13.view.FileOperation
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.icu.text.UnicodeSet.EMPTY
import android.net.Uri
import android.net.Uri.EMPTY
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mobile.macs_13.R
import com.mobile.macs_13.com.mobile.macs_13.controller.uploadController

class UploadFile : AppCompatActivity() {
    lateinit var button: Button
    val pdf: Int = 0
    lateinit var uri: Uri
    lateinit var mStorage: StorageReference
    lateinit var choosebtn: Button
    private var radioGroup: RadioGroup? = null
    private  var selectedRadioButton: RadioButton? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        uri = Uri.EMPTY
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_file)
        supportActionBar?.title = "Documents"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mStorage = FirebaseStorage.getInstance().getReference("uploads")
        button = findViewById(R.id.uploadButton)
        choosebtn = findViewById(R.id.chooseButton)
        radioGroup = findViewById(R.id.radiogroup);
        radioGroup?.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                // The flow will come here when
                // any of the radio buttons in the radioGroup
                // has been clicked
                // Check which radio button has been clicked
                // Get the selected Radio Button
                selectedRadioButton= group
                    .findViewById<View>(checkedId) as RadioButton
            })
        choosebtn.setOnClickListener { view: View? ->
            val intent = Intent()
            intent.type = "application/pdf"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), pdf)
        }
        button.setOnClickListener{
            if(uri === Uri.EMPTY) {
                    Toast.makeText(applicationContext,"Please Choose a File!",Toast.LENGTH_SHORT).show()
                }
            else
            {
                upload()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == pdf) {
                uri = data!!.data!!
                val chooseFileText = findViewById<EditText>(R.id.chooseFileText)
                chooseFileText?.setText(uri.toString())
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    @SuppressLint("SuspiciousIndentation")
    private fun upload(){
        var mRefrence = mStorage.child(uri.lastPathSegment.toString())
            mRefrence.putFile(uri).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                var url = taskSnapshot!!.uploadSessionUri
                Toast.makeText(this, "Successfully uploaded "+selectedRadioButton?.text.toString(), Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Fail to upload", Toast.LENGTH_LONG).show()
            }
    }
    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
