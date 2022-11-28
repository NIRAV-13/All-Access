package com.mobile.macs_13.controller

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile

/*
* This is a controller class for user login related functionalities login and forgot password
* It makes a connection with Firebase auth and db to sign in the user into the application
* using Firebase signInWithEmailAndPassword method
* On successful sign in it makes a call to the firestore to retrieve the information about the logged in
* user and set it to the globally accessible UserProfile object.
* References: 1 - https://firebase.google.com/docs/auth/android/start#kotlin+ktx
*             2 - https://firebase.google.com/docs/firestore/query-data/get-data#kotlin+ktx
*             3 - https://stackoverflow.com/questions/46995080/how-do-i-get-the-document-id-for-a-firestore-document-using-kotlin-data-classes
* @author: Meghna Kumar
* */

class LoginController {

    //method to log in the user and retrieve  their user profile information
    fun login(email: String, password: String, function: (Boolean) -> Unit) {
        FirebaseRefSingleton.getFirebaseDB().firestoreSettings =
            FirebaseFirestoreSettings.Builder().build()
        // logic for user login
        FirebaseRefSingleton.getFirebaseAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = FirebaseRefSingleton.getFirebaseAuth().currentUser
                    if (currentUser != null) {
                        Log.e(
                            "Login",
                            "currentUser: " + currentUser.uid + "currentUser.email: " + currentUser
                        )

                        FirebaseRefSingleton.getFirebaseDB()
                            .collection("User Profile")
                            .document(currentUser.uid)
                            .get()
                            .addOnSuccessListener { documents ->
                                User.setCurrentUserProfile(documents.toObject(UserProfile::class.java)!!)
                                Log.d("USER", User.getCurrentUserProfile().uid.toString())
                            }
                    } else {
                        function(false)
                    }
                } else {
                    function(false)
                }
                function(true)
            }
    }

    //method to send a password reset link incase of forgot password.
    fun forgotPassword(email: String, function: (Boolean) -> Unit) {
        FirebaseRefSingleton.getFirebaseAuth().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    function(true)
                } else {
                    function(false)
                }
            }
    }
}