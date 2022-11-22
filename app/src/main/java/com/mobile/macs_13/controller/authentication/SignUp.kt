package com.mobile.macs_13.controller.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobile.macs_13.R
import com.mobile.macs_13.model.chat.UserModel

class  SignUp : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var edtName: EditText
    private lateinit var signUpBtn: Button
    private lateinit var loginAuth : FirebaseAuth
    private lateinit var userDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        loginAuth= FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail= findViewById(R.id.edt_email)
        edtPass= findViewById(R.id.edt_password)
        signUpBtn= findViewById(R.id.signUpButton)

        signUpBtn.setOnClickListener{
            val name= edtName.text.toString()
            val email = edtEmail.text.toString()
            val pass= edtPass.text.toString()
            
            signUpUser(name,email, pass)
        }
    }

    private fun signUpUser(name: String,email: String, pass: String) {
    //logic for user creation using firebase

        loginAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // adding user to the database

                    addUserToDb(name, email, loginAuth.currentUser?.uid!!)
                    // Sign in success, update UI with the signed-in user's information\
                    val loginIntent= Intent(this@SignUp, Login::class.java)
                    finish()
                    startActivity(loginIntent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp,"Error Occurred!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDb(name: String, email: String, uid: String) {

        userDbRef= FirebaseDatabase.getInstance().getReference()
        userDbRef.child("users").child(uid).setValue(UserModel(name,email,uid))
    }
}