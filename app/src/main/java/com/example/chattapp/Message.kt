package com.example.chattapp

class Message {
    var message:String?=null
    var senderId:String?=null
   var timestamp:Long?=0

    constructor()
    constructor(message:String?,senderId:String?,timestamp:Long?){
        this.message=message
        this.senderId=senderId
        this.timestamp=timestamp

    }
}