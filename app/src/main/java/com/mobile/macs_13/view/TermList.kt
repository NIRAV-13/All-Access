package com.mobile.macs_13.com.mobile.macs_13.view

import com.mobile.macs_13.com.mobile.macs_13.model.Term
// object to manipulate the different terms of student
object TermList {

    private var termList = mutableListOf<String>()

    fun addTerm(term: Term){
        if(!termList.contains(term.term)){
            termList.add(term.term.toString())
        }
    }

    fun getTerms(): MutableList<String> {
        return termList
    }

}

