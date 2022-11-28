package com.mobile.macs_13.view.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.controller.LoginController

/**
 * This class returns the view for the forgot password page of the app
 * It call the forgot password activity and holds the logic calling the controller class
 * to access the reset password link method
 * On successfull email sent to the user, it displays a toast message and redirects to the login activity
 * References: 1 - https://www.youtube.com/watch?v=nVhPqPpgndM
 *             2- https://stackoverflow.com/questions/12358485/android-open-activity-without-save-into-the-stack
 **/
class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpswd)

        val resetpswdBtn: Button = findViewById(R.id.resetPassword)
        val resetPasswordEmail: EditText = findViewById(R.id.forgotpswdEmail)
        Log.e("Forgot Password", "currentUser: $resetPasswordEmail")
        resetpswdBtn.setOnClickListener {
            val email: String = resetPasswordEmail.text.toString().trim { it <= ' ' }
            val loginController = LoginController()
            Log.e("Forgot Password", "currentUser: $email")
            if (email.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter email address to reset password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loginController.forgotPassword(email) { success ->
                    if (success) {
                        Toast.makeText(
                            this,
                            "Email sent successfully, please reset your password",
                            Toast.LENGTH_SHORT
                        ).show()
                        resetPasswordEmail.text.clear()
                        Handler().postDelayed(Runnable {
                            val loginIntent = Intent(this, Login::class.java)
                            //Reference for Handler code: https://stackoverflow.com/questions/12358485/android-open-activity-without-save-into-the-stack
                            loginIntent.flags = loginIntent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                            startActivity(loginIntent)
                        }, 3000)
                    } else {
                        Toast.makeText(
                            this,
                            "There was some issue, email could not be sent. Please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

    }

}