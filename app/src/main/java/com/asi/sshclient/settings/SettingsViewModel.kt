package com.asi.sshclient.settings

import androidx.lifecycle.ViewModel
import com.asi.sshclient.auth.AuthService
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    var authService: AuthService,
    var serverService: ServerService
) : ViewModel() {
    private lateinit var currentServer: Server
    get

    fun changeCurrentServer(index: Int) {
        val auth = authService.findAuthById(index)
        currentServer = serverService.getServerByAuth(auth)
    }

    fun modifySettings(index: Int) {
        //TODO modify and save settings after we've discussed what those settings will be
    }


}
