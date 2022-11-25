package com.mobile.macs_13.controller.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mobile.macs_13.AdvisorActivity
import com.mobile.macs_13.R
import com.mobile.macs_13.StudentActivity
import com.mobile.macs_13.controller.about.AboutUs
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile

// https://firebase.google.com/docs/auth/android/start#kotlin+ktx
// https://firebase.google.com/docs/firestore/query-data/get-data#kotlin+ktx
// https://stackoverflow.com/questions/46995080/how-do-i-get-the-document-id-for-a-firestore-document-using-kotlin-data-classes

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var aboutUsBtn: Button
    private lateinit var forgotpswd: TextView
    private lateinit var loginAuth: FirebaseAuth
    private val TAG = "Login"

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
        aboutUsBtn = findViewById(R.id.aboutUsButton)
        forgotpswd = findViewById(R.id.forgot_pswd)

        loginBtn.setOnClickListener {
            val email = edtEmail.text.toString()
            val passwd = edtPass.text.toString()
            if (email.isNotEmpty() && passwd.isNotEmpty())
                login(email, passwd)
            else
                Toast.makeText(this@Login, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()
        }

        aboutUsBtn.setOnClickListener {
            val aboutUsIntent = Intent(this, AboutUs::class.java)
            startActivity(aboutUsIntent)
        }

        forgotpswd.setOnClickListener {
            val forgotPswdIntent = Intent(this, ForgotPassword::class.java)
            forgotPswdIntent.setFlags(forgotPswdIntent.getFlags() or Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(forgotPswdIntent)
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    private fun login(email: String, password: String) {

        // logic for user login
        loginAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val currentUser = loginAuth.currentUser
                    if (currentUser != null) {
                        Log.e("Login", "currentUser: " + currentUser.uid + "currentUser.email: " + currentUser)

                        val mFirestore = FirebaseFirestore.getInstance()
                        mFirestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

                        mFirestore
                            .collection("User Profile")
                            .document(currentUser.uid)
                            .get()
                            .addOnSuccessListener { documents ->
                                val userProfile = documents.toObject(UserProfile::class.java)!!
                                User.setCurrentUserProfile(userProfile)
                                Log.d("USER", User.getCurrentUserProfile().toString())
                            }
                    }

                    if(User.getCurrentUserProfile().type ==1){
                        val studentHomePageIntent = Intent(this@Login, StudentActivity::class.java)
                        finish()
                        startActivity(studentHomePageIntent)  
                    }
                    else if(User.getCurrentUserProfile().type==2){
                        val advisorHomePageIntent = Intent(this@Login, AdvisorActivity::class.java)
                        finish()
                        startActivity(advisorHomePageIntent)
                    }
                    else{
                        val instructorHomePageIntent = Intent(this@Login, StudentActivity::class.java)
                        finish()
                        startActivity(instructorHomePageIntent)
                    }
                    

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "User doesn't exist!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
