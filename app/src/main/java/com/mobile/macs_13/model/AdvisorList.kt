package com.mobile.macs_13.model

object AdvisorList {

    private var advisorList = mutableListOf<Advisor>()

    fun addAdvisor(advisor: Advisor){
        if(!advisorList.contains(advisor))
        advisorList.add(advisor)
    }


    fun getAdvisors(): MutableList<Advisor> {
        return advisorList;
    }

}