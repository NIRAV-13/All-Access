package com.mobile.macs_13.model

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class PushStudentNotification {

    companion object {

        public fun pushStudentHomeNotification(notificationData: StudentNotificationData) {

            val notification = hashMapOf(
                "notifTitle" to notificationData.notifTitle,
                "notifText" to notificationData.notifText,
                "timestamp" to FieldValue.serverTimestamp()
            )
            val db = FirebaseFirestore.getInstance()
            db.collection("StudentHomeNotifications")
                .add(notification)
                .addOnSuccessListener { documentReference ->
                    Log.d("Info", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Info", "Error adding document", e)
                }
        }
    }
}