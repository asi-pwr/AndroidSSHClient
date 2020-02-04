package com.asi.sshclient

import android.app.Application

class MyApplication : Application() {

     val appComponent : ApplicationComponent by  lazy { DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build() }
}