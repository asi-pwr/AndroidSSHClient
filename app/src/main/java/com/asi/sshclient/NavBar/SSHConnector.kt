package com.asi.sshclient.NavBar

import com.asi.sshclient.auth.AuthData
import com.asi.sshclient.settings.ServerSettings

interface SSHConnector {
    fun connect(authData: AuthData, settings: ServerSettings)
}