package com.asi.sshclient

import android.content.SharedPreferences

typealias host = String
typealias user = String
typealias password = String

class AuthStorage(val pref:SharedPreferences) {

    fun getListOfPasses():List<Triple<host,user,password>> {
        val setOfPasses = pref.getStringSet(PASSES_PREFERENCE,null) ?: setOf()
        val listOfLists = setOfPasses.toList().map { it.split(SPLIT_SIGN) }
        return listOfLists.map { Triple(it[0],it[1],it[2]) }
    }

}