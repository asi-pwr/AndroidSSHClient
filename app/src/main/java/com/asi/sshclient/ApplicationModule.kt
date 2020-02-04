package com.asi.sshclient

import android.content.Context
import com.asi.sshclient.auth.*
import com.asi.sshclient.settings.ServerService
import com.asi.sshclient.settings.ServerServiceImpl
import com.asi.sshclient.settings.SettingsFragment
import com.asi.sshclient.settings.SettingsViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val context: Context) {

    @Provides
    fun appContext() = context

    @Provides
    @Singleton
    fun getSharedPreferences(context: Context) =
        context.getSharedPreferences(
            "PREF",
            Context.MODE_PRIVATE
        )

    @Provides
    fun getAuthService(storage: AuthServiceImpl): AuthService = storage

    @Provides
    fun getServerService(service: ServerServiceImpl): ServerService = service

}

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(authListActivity: AuthListActivity)

    fun inject(settingsFragment: SettingsFragment)

    fun inject(authFragment: AuthFragment)

}