package com.mobile.macs_13.controller.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseRefSingleton {

    private val firebaseDB = Firebase.firestore
    private val loginAuth = FirebaseAuth.getInstance()

    fun getFirebaseDB(): FirebaseFirestore {
        return firebaseDB
    }

    fun getFirebaseAuth(): FirebaseAuth{
        return loginAuth
    }
}