package com.asi.sshclient.NavBar

import com.asi.sshclient.auth.AuthData
import com.asi.sshclient.auth.AuthService
import com.asi.sshclient.settings.ServerSettings
import com.asi.sshclient.settings.ServerSettingsService
import javax.inject.Inject


class NavBarViewModel @Inject constructor(
    val ssh: SSHConnector,
    val authService: AuthService,
    val settingsService: ServerSettingsService,
    val alertFunction: (String) -> Unit
) {
    private lateinit var currentAuthData: AuthData
    private lateinit var currentSettings: ServerSettings
    private val NOT_INITIALIZED = "please choose passes"
    private val NO_SUCH_AUTH = "no such auth"

    fun connect() {
        if (::currentAuthData.isInitialized && ::currentSettings.isInitialized) {
            ssh.connect(currentAuthData, currentSettings)
        } else alertFunction(NOT_INITIALIZED)
    }

    fun changeCurrentAuth(id: Int) {
        authService.findAuthById(id)
    }

    fun changeData(authDataId: Int){
        val data = authService.getAuthDataById(authDataId)
        if (data != null){
            currentAuthData = data
            currentSettings = settingsService.getServerByAuth(data)
        }else alertFunction(NO_SUCH_AUTH)
    }
}