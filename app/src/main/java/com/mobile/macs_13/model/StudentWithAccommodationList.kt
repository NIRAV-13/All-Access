package com.mobile.macs_13.model

import com.mobile.macs_13.controller.InstructorController

object StudentWithAccommodationList {

    private var studentWithAccommodationList = mutableListOf<StudentAccomRequestModel>()

    fun addStudentWithAccommodation(studentWithAccommodation: StudentAccomRequestModel){
        if(!studentWithAccommodationList.contains(studentWithAccommodation))
            studentWithAccommodationList.add(studentWithAccommodation)
    }

    fun getStudentsWithAccommodation(): MutableList<StudentAccomRequestModel> {
        return studentWithAccommodationList;
    }

    fun getStudentDetails(id:Int): StudentAccomRequestModel {
        return studentWithAccommodationList.get(id);
    }
}