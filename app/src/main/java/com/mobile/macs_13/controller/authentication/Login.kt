package com.mobile.macs_13.controller.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobile.macs_13.R
import com.mobile.macs_13.view.chat.ChatView

// https://firebase.google.com/docs/auth/android/start#kotlin+ktx

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button
    private lateinit var loginAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //to remove the action bar
        supportActionBar?.hide()

        // initialize firebase authentication
        loginAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPass = findViewById(R.id.edt_password)
        loginBtn = findViewById(R.id.loginButton)
        signUpBtn = findViewById(R.id.signUpButton)

        loginBtn.setOnClickListener {
            val email = edtEmail.text.toString()
            val passwd = edtPass.text.toString()
            if (email.isNotEmpty() && passwd.isNotEmpty())
                login(email, passwd)
            else
                Toast.makeText(this@Login, "Please enter email and password", Toast.LENGTH_SHORT).show()
        }

        signUpBtn.setOnClickListener {
            val signUpIntent = Intent(this, SignUp::class.java)
            startActivity(signUpIntent)
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = loginAuth.currentUser
        if (currentUser != null) {
            Log.e("Login", "currentUser: " + currentUser.uid + "currentUser.email: " + currentUser)



            val loginIntent = Intent(this@Login, ChatView::class.java)
            finish()
            startActivity(loginIntent)
        }
    }

    private fun login(email: String, password: String) {

        // logic for user login

        loginAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val loginIntent = Intent(this@Login, ChatView::class.java)
                    finish()
                    startActivity(loginIntent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "User doesn't exist!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}