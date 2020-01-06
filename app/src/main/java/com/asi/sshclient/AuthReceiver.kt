package com.asi.sshclient

interface AuthReceiver {

    fun receiveAuth(user:String,host:String,password:String) : Boolean
}