package com.mobile.macs_13.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable



data class UserProfile (

    @SerializedName("Course" ) var course : String? = null,
    @SerializedName("Email"  ) var email  : String? = null,
    @SerializedName("Name"   ) var name   : String? = null,
    @SerializedName("Type"   ) var type   : Int?    = null


) : java.io.Serializable {
    override fun toString(): String {
        return "UserProfile(course=$course, email=$email, name=$name, type=$type)"
    }
}