package com.mobile.macs_13.com.mobile.macs_13.model


import java.util.Date


data class uploadocument(
    var uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var program: String? = null,
    var course: String? = null,
    var year: String? = null,
    var term: String? = null,
    var impact: String? = null,
    var consent: String? = null,
    var status: String?= null,
    var timeStamp: Date? = null
):java.io.Serializable
