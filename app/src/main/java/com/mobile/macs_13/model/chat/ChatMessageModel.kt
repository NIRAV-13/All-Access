package com.mobile.macs_13.model.chat

// model for the chat messages sent by the user in the recycler view
class ChatMessageModel {

    var message: String?= null
    var sendUserID: String?= null

    constructor(){}

    constructor(message:String?,sendUser:String?)
    {
        this.message = message
        this.sendUserID = sendUserID

    }
}