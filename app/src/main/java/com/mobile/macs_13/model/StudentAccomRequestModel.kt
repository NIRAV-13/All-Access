package com.mobile.macs_13.model

import android.provider.ContactsContract.CommonDataKinds.Phone
import java.util.Date


data class StudentAccomRequestModel (
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
    var timeStamp : Date,
    var status : String?

)
