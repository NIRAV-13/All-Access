package com.mobile.macs_13.model


object StudentWithAccommodationList {

    private var studentWithAccommodationList = mutableListOf<AdvisorAccomRequestModel>()

    fun addStudentWithAccommodation(studentWithAccommodation: AdvisorAccomRequestModel){
        if(!studentWithAccommodationList.contains(studentWithAccommodation))
            studentWithAccommodationList.add(studentWithAccommodation)
    }

    fun getStudentsWithAccommodation(): MutableList<AdvisorAccomRequestModel> {
        return studentWithAccommodationList;
    }
}