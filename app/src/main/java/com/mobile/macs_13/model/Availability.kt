package com.mobile.macs_13.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

// Data class for availability.
data class Availability(@DocumentId val availabilityId: String? = null,
                        val advisorEmail: String? = null,
                        val isAvailable: Boolean? = null,
                        val startTime: Timestamp? = null,
                        val endTime: Timestamp? = null): java.io.Serializable
