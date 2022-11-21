package com.mobile.macs_13.model.chat

// model for displaying the users in the recycler view
class UserDemoModel {

    var name:String?= null
    var email:String?=null
    var uid:String?=null

    constructor(){}

    constructor(name:String?,email:String?, uid:String?)
    {
        this.name= name
        this.email= email
        this.uid=uid
    }
}