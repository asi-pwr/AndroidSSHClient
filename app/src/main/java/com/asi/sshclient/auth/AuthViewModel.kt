package com.asi.sshclient.auth

import android.content.SharedPreferences
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

const val PASSES_PREFERENCE = "password"
const val SPLIT_SIGN = "{|}"

class AuthViewModel @Inject constructor(var service: AuthService) : ViewModel() {
    var user = ""
    var host = ""
    var password = ""
    var error = ""
    private val ERROR_MESSAGE = "Wrong data"
    private val FATAL_ERROR_MESSAGE = "restart app"
    private val SUCCESS_MESSAGE = "Ok"


    fun submit() {
        if (!(validate()))
            signalError()
        else {
            saveToSharedPreferences()
            signalSuccess()
        }
    }

    fun signalError() {
        error = ERROR_MESSAGE
    }

    fun signalSuccess() {
        error = SUCCESS_MESSAGE
    }

    fun signalFatalError(){
        error = FATAL_ERROR_MESSAGE
    }

    fun saveToSharedPreferences() {
        service.addAuthData(AuthData(host, user, password))
    }

    fun validate() = host.contains('.')


}