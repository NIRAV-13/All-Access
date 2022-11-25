package com.mobile.macs_13.model

import com.google.firebase.firestore.DocumentId
import java.util.*

data class AppointmentDetails(@DocumentId val appointmentId: String? = null,
                              var advisorEmail: String? = null,
                              var studentEmail: String? = null,
                              var startTime: Date? = null,
                              var endTime: Date? = null,
                              var status: Boolean? = null)
