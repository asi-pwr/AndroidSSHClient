package com.asi.sshclient.settings

import com.asi.sshclient.auth.AuthData
import javax.inject.Inject
import javax.inject.Singleton

data class Server(val placeholder:Int)

interface ServerService {
    fun getServerByAuth(auth: AuthData):Server
}

@Singleton
class ServerServiceImpl @Inject constructor(): ServerService{
    override fun getServerByAuth(auth: AuthData): Server =
        Server(0)
}