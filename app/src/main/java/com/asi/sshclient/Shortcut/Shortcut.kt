package com.asi.sshclient.Shortcut

data class Shortcut(val name:String,val procedure:String)

interface ShortcutService{
    fun addShortcut(name:String, procedure: String):Boolean
    fun getShortcut(name:String)
}

class ShortcutServiceImpl:ShortcutService{
    override fun addShortcut(name: String, procedure: String): Boolean {
        //TODO zrobimy to na bazie bo shared pref mnie zabija
        return false
    }

    override fun getShortcut(name: String) {
        //TODO tez
    }
}