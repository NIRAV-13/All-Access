package com.mobile.macs_13.model


import com.google.firebase.Timestamp
import java.util.*

data class AdvisorAccomRequestModel(
    var uid: String? = null,
    var name   : String? = null,
    var email  : String? = null,
    var phone: String? = null,
    var program  : String? = null,
    var course : String? = null,
    var year  : String? = null,
    var term: String? = null,
    var impact : String?= null,
    var consent : String?= null,
    var timeStamp : Date? = null,
    var status : String? = null

): java.io.Serializable