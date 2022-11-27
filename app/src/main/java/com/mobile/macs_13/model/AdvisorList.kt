package com.mobile.macs_13.model

// Singleton class for list of advisors.
object AdvisorList {

    // Mutable list of advisors.
    private var advisorList = mutableListOf<UserProfile>()

    // Adding advisor to the list.
    fun addAdvisor(advisor: UserProfile){
        if(!advisorList.contains(advisor)) {
            advisorList.add(advisor)
        }
    }

    // Fetching all advisors.
    fun getAdvisors(): MutableList<UserProfile> {
        return advisorList;
    }

}