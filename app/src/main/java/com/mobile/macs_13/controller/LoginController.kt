package com.mobile.macs_13.controller

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mobile.macs_13.controller.utils.FirebaseRefSingleton
import com.mobile.macs_13.controller.utils.User
import com.mobile.macs_13.model.UserProfile

class LoginController {

    fun login(email: String, password: String, function: (Boolean) -> Unit) {
        FirebaseRefSingleton.getFirebaseDB().firestoreSettings = FirebaseFirestoreSettings.Builder().build()
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
                    }
                    else{
                        function(false)
                    }

                }
                else{
                    function(false)
                }
                function(true)
            }
    }

    fun forgotPassword(email: String,function: (Boolean) -> Unit){
        FirebaseRefSingleton.getFirebaseAuth().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    function(true)

                } else{
                    function(false)
                }
            }
    }
}