package com.mobile.macs_13.model


import java.io.Serializable


data class UserProfile (
    var uid : String? = null,
    var name : String? = null,
    var email : String? = null,
    var phone : String? = null,
    var program  : String? = null,
     var course : String? = null,
    var year  : String? = null,
    var imageLink: String? = null,
    var type   : Int?    = null
): Serializable{
    override fun toString(): String {
        return "UserProfile(uid=$uid, name=$name, email=$email, phone=$phone, program=$program, course=$course, year=$year, imageLink=$imageLink, type=$type)"
    }
}