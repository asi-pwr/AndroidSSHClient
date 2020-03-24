package com.asi.sshclient.settings

import com.asi.sshclient.auth.AuthData
import javax.inject.Inject
import javax.inject.Singleton

data class ServerSettings(val placeholder:Int)

interface ServerSettingsService {
    fun getServerByAuth(auth: AuthData):ServerSettings
}

@Singleton
class ServerSettingsServiceImpl @Inject constructor(): ServerSettingsService{
    override fun getServerByAuth(auth: AuthData): ServerSettings =
        ServerSettings(0)
}