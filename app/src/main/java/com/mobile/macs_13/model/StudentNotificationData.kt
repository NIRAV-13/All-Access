package com.mobile.macs_13.model

/**
 * Student Notification Data Object Model
 * @author Ankush Mudgal
 * @property notifTitle
 * @property notifText
 * @constructor Create empty Student notification data
 */
data class StudentNotificationData(val notifTitle: String? = null, val notifText: String? = null) :
    java.io.Serializable {

    override fun toString(): String {
        return "StudentNotificationData(notifTitle='$notifTitle', notifText='$notifText')"
    }
}
