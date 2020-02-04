package com.asi.sshclient

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.asi.sshclient.auth.AuthFragment
import com.asi.sshclient.settings.SettingsFragment


class MainActivity : AppCompatActivity(),
    AuthFragment.AuthFragmentListener,
    SettingsFragment.SettingsFragmentListener {
    override fun backToPreviousFragment() {
        popFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun popFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun openLoginAddPage() {
        replaceFragment(AuthFragment())
    }
}
