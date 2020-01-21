package com.asi.sshclient

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asi.sshclient.ui.main.MainFragment
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.UserInfo


class MainActivity : AppCompatActivity(), AuthReceiver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthFragment(this))
                .commitNow()
        }

    }

    override fun receiveAuth(user: String, host: String, password: String): Boolean {
        try {
            val jsch = JSch()
            val session = jsch.getSession(host,user)
            session.setPassword(password);
            session.userInfo = ui
            session.connect()

            val a = getSharedPreferences("PREF", Context.MODE_PRIVATE)
            return true
        }catch (e: JSchException){
            return false
        }
    }

    object ui : UserInfo {
        override fun promptYesNo(message: String?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun showMessage(message: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun promptPassphrase(message: String?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getPassphrase(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getPassword(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun promptPassword(message: String?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

}
