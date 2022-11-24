package com.mobile.macs_13.model


import java.util.*

data class AppointmentDetails(var advisorEmail: String,
                              var studentEmail: String,
                              var startTime: Date,
                              var endTime: Date,
                              var status: Boolean)
