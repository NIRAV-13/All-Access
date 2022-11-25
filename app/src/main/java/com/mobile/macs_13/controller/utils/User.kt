package com.mobile.macs_13.controller.utils

import android.util.Log
import com.mobile.macs_13.model.UserProfile

object User {

    private var userProfile: UserProfile = UserProfile()

    init{
        Log.d("Info", "UserProfile Created.")
    }

    fun getCurrentUserProfile(): UserProfile{
        return userProfile
    }

    fun setCurrentUserProfile(incomingUserProfile: UserProfile){
        this.userProfile = incomingUserProfile
    }

}