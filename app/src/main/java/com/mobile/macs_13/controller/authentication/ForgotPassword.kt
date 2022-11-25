package com.mobile.macs_13.controller.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mobile.macs_13.R

//Reference Link: https://www.youtube.com/watch?v=nVhPqPpgndM
//https://stackoverflow.com/questions/12358485/android-open-activity-without-save-into-the-stack
class ForgotPassword : AppCompatActivity() {
    private lateinit var resetpswdBtn: Button
    private lateinit var resetPasswordEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpswd)

        resetpswdBtn = findViewById(R.id.resetPassword)
        resetPasswordEmail = findViewById(R.id.forgotpswdEmail)
        Log.e("Forgot Password", "currentUser: " + resetPasswordEmail)
        resetpswdBtn.setOnClickListener {
            val email: String = resetPasswordEmail.text.toString().trim { it <= ' ' }
            Log.e("Forgot Password", "currentUser: " + email)
            if (email.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter email address to reset password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Email sent successfully, please reset your password",
                                Toast.LENGTH_SHORT
                            ).show()
                            val loginIntent = Intent(this, Login::class.java)
//                            https://stackoverflow.com/questions/12358485/android-open-activity-without-save-into-the-stack
                            loginIntent.setFlags(loginIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
                            startActivity(loginIntent)
                        } else {
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }

    }

}