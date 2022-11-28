package com.mobile.macs_13.com.mobile.macs_13.model

object CourseList {

    private var courseList = mutableListOf<String>()

    fun addCourse(course: Course){
        if(!courseList.contains(course.course)){
            courseList.add(course.course.toString())
        }
    }

    fun getCourses(): MutableList<String> {
        return courseList
    }

}