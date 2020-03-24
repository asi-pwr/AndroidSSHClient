package com.asi.sshclient.auth

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

data class AuthData(val host: String, val user: String, val password: String)

@Singleton
interface AuthService {
    fun getListOfAuthData(): List<AuthData>
    fun getAuthDataById(id: Int): AuthData?
    fun addAuthData(data: AuthData)
    fun findAuthById(id :Int):AuthData
}

@Singleton
class AuthServiceImpl @Inject constructor(var pref: SharedPreferences) : AuthService {

    override fun addAuthData(data: AuthData) {
        val concat = data.host + SPLIT_SIGN + data.user + SPLIT_SIGN + data.password
        val passwords = (pref.getStringSet(PASSES_PREFERENCE, null) ?: setOf()) + concat
        with(pref.edit()) {
            putStringSet(PASSES_PREFERENCE, passwords)
            apply()
        }
    }

    override fun getAuthDataById(id: Int): AuthData? {
        return  getListOfAuthData().run{
            if (size > id) get(id) else null
        }
    }

    override fun getListOfAuthData(): List<AuthData> {
        val setOfPasses = pref.getStringSet(PASSES_PREFERENCE, null) ?: setOf()
        val listOfLists = setOfPasses.toList().map { it.split(SPLIT_SIGN) }
        return listOfLists.map { AuthData(it[0], it[1], it[2]) }
    }

    override fun findAuthById(id :Int) =
      getListOfAuthData()[id]

}

