package com.mobile.macs_13.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable



data class UserProfile (
    var uid: String? = null,
    var name   : String? = null,
    var email  : String? = null,
    var phone: String? = null,
    var program  : String? = null,
     var course : String? = null,
    var year  : String? = null,
    var imageLink: String? = null,
    var term: String? = null,
    var type   : Int?    = null
){
    override fun toString(): String {
        return "UserProfile(uid=$uid, name=$name, email=$email, phone=$phone, program=$program, course=$course, year=$year, imageLink=$imageLink, term=$term, type=$type)"
    }
}