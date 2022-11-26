package com.mobile.macs_13.model

import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object AddRemindNotification {

    private val BUFFER_TIME_TO_SEND_NOTIFICATION_IN_SECONDS: Long = 3600
    private var sendNotificationTime: Long = 0

    fun sendNotification(appointmentDetails: AppointmentDetails, context: Context){

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("AppointmentDetails").
        document(appointmentDetails.appointmentId.toString())
            .get()
            .addOnSuccessListener { document ->

                val documentData = document.data
                    val appointmentStartTimeStamp = (documentData?.get("startTime") as Timestamp)
                    val appointmentTime = appointmentStartTimeStamp.seconds
                    val advisorEmail: String = documentData["advisorEmail"].toString()

                    val currentTime = System.currentTimeMillis()/1000
                    sendNotificationTime = (appointmentTime - currentTime) - BUFFER_TIME_TO_SEND_NOTIFICATION_IN_SECONDS
                    this.oneTimeWork(appointmentStartTimeStamp,advisorEmail, context)
                }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents: ", exception)
            }

    }

    private fun convertTimeStampToDate(time: Timestamp): String {
        val milliseconds = time.seconds * 1000 + time.nanoseconds / 1000000
        val sdf = SimpleDateFormat("M/dd/yyyy hh:mm:ss")
        val netDate = Date(milliseconds)
        val date = sdf.format(netDate).toString()
        return date
    }

    private fun oneTimeWork(appointmentTimeStamp: Timestamp, advisorEmail: String, context: Context) {

        val constraints = Constraints.Builder().
        setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val appointmentDate = convertTimeStampToDate(appointmentTimeStamp)

        val data = Data.Builder()
            .putString("advisorEmail",advisorEmail)
            .putString("appointmentDate", appointmentDate)
            .build()

        val request = OneTimeWorkRequest.Builder(
            MyWorker::class.java
        )
            .setInitialDelay(sendNotificationTime, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request);

    }

}