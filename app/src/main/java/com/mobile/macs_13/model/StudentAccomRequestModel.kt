package com.mobile.macs_13.model


import com.google.firebase.firestore.DocumentId
import java.util.Date

// model class for the accommodation details of student
data class StudentAccomRequestModel(
    var uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var program: String? = null,
    var course: String? = null,
    var year: String? = null,
    var term: String? = null,
    var imageLink: String? = null,
    var impact: String? = null,
    var consent: String? = null,
    var status: String?= null,
    var timeStamp: Date? = null,
    @DocumentId var requestID:String? = null
) :java.io.Serializable
