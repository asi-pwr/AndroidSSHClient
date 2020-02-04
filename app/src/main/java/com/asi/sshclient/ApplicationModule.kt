package com.asi.sshclient

import android.content.Context
import com.asi.sshclient.auth.*
import com.asi.sshclient.settings.ServerSettingsService
import com.asi.sshclient.settings.ServerSettingsServiceImpl
import com.asi.sshclient.settings.SettingsFragment
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
    fun getServerService(service: ServerSettingsServiceImpl): ServerSettingsService = service

}

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(authListActivity: AuthListActivity)

    fun inject(settingsFragment: SettingsFragment)

    fun inject(authFragment: AuthFragment)

}