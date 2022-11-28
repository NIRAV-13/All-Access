package com.mobile.macs_13.com.mobile.macs_13.model

// object for manipulating the programs of student
object ProgramList {

    private var programList = mutableListOf<String>()

    fun addProgram(program: Program){
        if(!programList.contains(program.program)){
            programList.add(program.program.toString())
        }
    }

    fun getPrograms(): MutableList<String> {
        return programList
    }

}