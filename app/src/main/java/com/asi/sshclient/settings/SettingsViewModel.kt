package com.asi.sshclient.settings

import androidx.lifecycle.ViewModel
import com.asi.sshclient.auth.AuthService
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    var authService: AuthService,
    var serverSettingsService: ServerSettingsService
) : ViewModel() {
    private lateinit var currentServerSettings: ServerSettings
    get

    fun changeCurrentServer(index: Int) {
        val auth = authService.getListOfAuthData()[index]
        currentServerSettings = serverSettingsService.getServerByAuth(auth)
    }

    fun modifySettings(index: Int) {
        //TODO modify and save settings after we've discussed what those settings will be
    }


}
