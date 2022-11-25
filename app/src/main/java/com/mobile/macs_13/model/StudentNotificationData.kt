package com.mobile.macs_13.model

data class StudentNotificationData(val notifTitle: String? = null, val notifText: String? = null) {


    override fun toString(): String {
        return "StudentNotificationData(notifTitle='$notifTitle', notifText='$notifText')"
    }
}
