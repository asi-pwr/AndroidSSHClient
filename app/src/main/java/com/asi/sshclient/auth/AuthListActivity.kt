package com.asi.sshclient.auth

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.asi.sshclient.R

import kotlinx.android.synthetic.main.activity_auth_list.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.asi.sshclient.MyApplication
import javax.inject.Inject
import android.app.Activity
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.asi.sshclient.settings.AUTH_PASS_INDEX


interface AuthListener{
    fun onClick(position:Int)
}

class AuthListActivity() : AppCompatActivity(),AuthListener {

    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_list)
        (application as MyApplication).appComponent.inject(this)
        val rvAuthList = findViewById(R.id.auth_list) as RecyclerView
        val authList = authService.getListOfAuthData()
        val adapter = AuthAdapter(authList.toTypedArray() + AuthData("a","b","c") + AuthData("d","e","f"),this)
        rvAuthList.adapter = adapter
        rvAuthList.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(position: Int) {
        val returnIntent = Intent()
        returnIntent.putExtra(AUTH_PASS_INDEX, position)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


}
