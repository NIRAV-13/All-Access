package com.mobile.macs_13.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mobile.macs_13.MainActivity
import com.mobile.macs_13.R

class LoginDemo : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var loginAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_demo)

        //to remove the action bar
        supportActionBar?.hide()

        // initialize firebase authentication
        loginAuth= FirebaseAuth.getInstance()

        edtEmail= findViewById(R.id.edt_email)
        edtPass= findViewById(R.id.edt_password)
        loginBtn= findViewById(R.id.loginButton)

       loginBtn.setOnClickListener{
           val email = edtEmail.text.toString()
           val passwd= edtPass.text.toString()

           login(email, passwd)
       }
    }

    private fun login(email: String, password: String) {

        // logic for user login

        loginAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val loginIntent= Intent(this@LoginDemo, MainActivity::class.java)
                    startActivity(loginIntent)

                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(this@LoginDemo,"User doesn't exist!",Toast.LENGTH_SHORT).show()
                }
            }
    }
}