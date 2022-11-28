package com.mobile.macs_13.controller.accomodation

import com.mobile.macs_13.model.AdvisorAccomRequestModel

object AccomodationList {

    private var accomodationList = mutableListOf<AdvisorAccomRequestModel>()

    fun addAccomodation(accomodation: AdvisorAccomRequestModel){
        if(!accomodationList.contains(accomodation))
            accomodationList.add(accomodation)
    }


    fun getAccomodations(): MutableList<AdvisorAccomRequestModel> {
        return accomodationList;
    }

    fun clearList(){
        accomodationList.clear()
    }

}