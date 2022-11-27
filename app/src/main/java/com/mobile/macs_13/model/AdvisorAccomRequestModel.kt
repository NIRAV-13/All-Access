package com.mobile.macs_13.model


import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

data class AdvisorAccomRequestModel(
    var uid: String? = null,
    var studentName: String? = null,
    var studentEmail: String? = null,
    var studentPhone: String? = null,
    var studentProgram: String? = null,
    var studentCourse: String? = null,
    var studentYear: String? = null,
    var studentTerm: String? = null,
    var studentDocs: String?,
    var studentImageLink: String? = null,
    var studentImpact: String? = null,
    var studentConsent: String? = null,
    var studentStatus: String?= null,
    var studentTimeStamp: Date? = null,
    var advisorName: String?=null,
    var advisorEmail: String?=null,
    var advisorImageLink: String?=null,
    var advisorPhone : String?=null,
    var documentId: String? = null
): java.io.Serializable