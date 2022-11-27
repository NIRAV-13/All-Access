package com.mobile.macs_13.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

// Data class for availability.
data class Availability(@DocumentId val availabilityId: String,
                        val advisorEmail: String,
                        val isAvailable: Boolean,
                        val startTime: Timestamp,
                        val endTime: Timestamp)
