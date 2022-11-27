package com.mobile.macs_13.model


import java.util.*

data class AdvisorAccomRequestModel(
    var uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var program: String? = null,
    var course: String? = null,
    var year: String? = null,
    var term: String? = null,
    var docs: String?,
    var imageLink: String? = null,
    var impact: String? = null,
    var consent: String? = null,
    var status: String?= null,
    var timeStamp: Date? = null,
    var advisorName: String?=null,
    var advisorEmail: String?=null,
    var advisorImageLink: String?=null,
    var advisorPhone : String?=null,
    var documentId: String? = null
): java.io.Serializable