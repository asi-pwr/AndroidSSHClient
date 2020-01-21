package com.asi.sshclient

import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import androidx.lifecycle.ViewModel

const val PASSES_PREFERENCE = "password"
const val SPLIT_SIGN = "{|}"

class AuthViewModel(val pref : SharedPreferences) : ViewModel() {
    var user = ""
    var host = ""
    var password = ""
    var error = ""
    private val ERROR_MESSAGE = "Wrong data"
    private val SUCCESS_MESSAGE = "Wrong data"


    fun submit() {
        if( !(validate()))
            signalError()
        else{
            saveToSharedPreferences()
        }
    }

    fun signalError(){
        error = ERROR_MESSAGE
    }

    fun signalSuccess(){
        error = SUCCESS_MESSAGE
    }

    fun saveToSharedPreferences(){
        val concat = host + SPLIT_SIGN + user + SPLIT_SIGN + password
        val passwords = (pref.getStringSet(PASSES_PREFERENCE, null) ?: setOf()) + concat
        with (pref.edit()) {
            putStringSet(PASSES_PREFERENCE,passwords)
            apply()
        }
    }

    fun validate() = host.contains('.')


}