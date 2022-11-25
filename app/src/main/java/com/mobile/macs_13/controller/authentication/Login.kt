package com.mobile.macs_13.controller.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mobile.macs_13.R
import com.mobile.macs_13.view.StudentAppointmentListActivity
import com.mobile.macs_13.view.StudentBookAppointmentHome
import com.mobile.macs_13.view.chat.ChatView

class  Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button
    private lateinit var loginAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //to remove the action bar
        supportActionBar?.hide()

        // initialize firebase authentication
        loginAuth= FirebaseAuth.getInstance()

        edtEmail= findViewById(R.id.edt_email)
        edtPass= findViewById(R.id.edt_password)
        loginBtn= findViewById(R.id.loginButton)
        signUpBtn= findViewById(R.id.signUpButton)

       loginBtn.setOnClickListener{
           val email = edtEmail.text.toString()
           val passwd= edtPass.text.toString()

           login(email, passwd)
       }

       signUpBtn.setOnClickListener{
//           val signUpIntent = Intent(this, SignUp::class.java)
           val test = Intent(this, StudentAppointmentListActivity::class.java)
           startActivity(test)

       }

    }

    private fun login(email: String, password: String) {

        // logic for user login

        loginAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val loginIntent= Intent(this@Login, ChatView::class.java)
                    finish()
                    startActivity(loginIntent)

                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(this@Login,"User doesn't exist!",Toast.LENGTH_SHORT).show()
                }
            }
    }
}