package com.mobile.macs_13.model

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context;
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobile.macs_13.MainActivity
import com.mobile.macs_13.R

// Worker class for sending reminder notifications.
class MyWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    companion object{
        const val CHANNEL_ID="1"
        const val NOTIFICATION = 1
    }

    @SuppressLint("NewApi")
    override fun doWork(): Result {

        showNotification(inputData.getString("name"),  inputData.getString("time"))
        return Result.success();
    }

    // Show notification method to send notification.
    @SuppressLint("NewApi")
    private fun showNotification(name: String?,  time: String?) {

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val flags = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            else -> FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, flags)

        val notificationText = "You have upcoming appointment with $name  at $time."
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Appointment Reminder")
            .setContentText(notificationText)
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(notificationText))
            .setContentIntent(pendingIntent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channelName = "Channel name"
            val channelDesc = "Channel desc"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
                description = channelDesc
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)){
            notify(NOTIFICATION, notification.build())
        }
    }
}