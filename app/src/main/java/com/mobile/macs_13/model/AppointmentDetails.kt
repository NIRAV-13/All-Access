package com.mobile.macs_13.model

import com.google.firebase.firestore.DocumentId
import java.io.Serializable
import java.util.*

data class AppointmentDetails(var advisorName : String? = null,
                              var advisorEmail : String? = null,
                              var advisorPhone : String? = null,
                              var advisorImageLink: String? = null,
                              var studentEmail: String ?= null,
                              var studentName : String? = null,
                              var studentPhone : String? = null,
                              var studentProgram  : String? = null,
                              var studentCourse : String? = null,
                              var studentYear  : String? = null,
                              var studentImageLink: String? = null,
                              var appointmentStartTime: Date? = null,
                              var appointmentEndTime: Date? = null,
                              var appointmentStatus: Boolean? = null,
                              var appointmentReason: String? = null,
                              @DocumentId val appointmentId: String? = null) : Serializable
