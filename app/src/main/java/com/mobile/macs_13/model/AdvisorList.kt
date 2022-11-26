package com.mobile.macs_13.model

object AdvisorList {

    private var advisorList = mutableListOf<UserProfile>()

    fun addAdvisor(advisor: UserProfile){
        if(!advisorList.contains(advisor))
        advisorList.add(advisor)
    }


    fun getAdvisors(): MutableList<UserProfile> {
        return advisorList;
    }

}