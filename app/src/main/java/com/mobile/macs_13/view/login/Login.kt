package com.mobile.macs_13.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.macs_13.AdvisorActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.StudentActivity
import com.mobile.macs_13.controller.Instructor.InstructorView
import com.mobile.macs_13.controller.LoginController
import com.mobile.macs_13.controller.about.AboutUs
import com.mobile.macs_13.controller.utils.User

/**
 * This class returns the view for the login page of the app
 * It call the login activity and holds the logic redirecting to the user specific landing pages
 * It also has the redirection logics for about us and forgot password activites
 * On click listener on login button first makes a call to login controller to sign in using db and gets the user profile data
 **/
class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var aboutUsBtn: Button
    private lateinit var forgotpswd: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //to remove the action bar
        supportActionBar?.hide()


        edtEmail = findViewById(R.id.edt_email)
        edtPass = findViewById(R.id.edt_password)
        loginBtn = findViewById(R.id.loginButton)
        aboutUsBtn = findViewById(R.id.aboutUsButton)
        forgotpswd = findViewById(R.id.forgot_pswd)

        //to log in to the application
        loginBtn.setOnClickListener {
            val email = edtEmail.text.toString()
            val passwd = edtPass.text.toString()
            if (email.isNotEmpty() && passwd.isNotEmpty()) {
                val loginController = LoginController()
                loginController.login(email, passwd){
                    success ->
                    if(success){
                        if (User.getCurrentUserProfile().type == 1) {
                            val studentHomePageIntent = Intent(this@Login, StudentActivity::class.java)
                            startActivity(studentHomePageIntent)
                        } else if (User.getCurrentUserProfile().type == 2) {
                            val advisorHomePageIntent = Intent(this@Login, AdvisorActivity::class.java)
                            startActivity(advisorHomePageIntent)
                        } else if(User.getCurrentUserProfile().type == 3) {
                            val instructorHomePageIntent = Intent(this@Login, InstructorView::class.java)
                            startActivity(instructorHomePageIntent)
                        }
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@Login, "User doesn't exist!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else
                Toast.makeText(this@Login, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()
        }

        //to redirect to the about us page of the application
        aboutUsBtn.setOnClickListener {
            val aboutUsIntent = Intent(this, AboutUs::class.java)
            startActivity(aboutUsIntent)
        }

        //to redirect to the forgot password page to reset the password
        forgotpswd.setOnClickListener {
            val forgotPswdIntent = Intent(this, ForgotPassword::class.java)
            forgotPswdIntent.setFlags(forgotPswdIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(forgotPswdIntent)
        }

    }
}
