package com.mobile.macs_13.controller.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseRefSingleton {

    private val firebaseDB = Firebase.firestore

    fun getFirebaseDB(): FirebaseFirestore {
        return firebaseDB
    }

}