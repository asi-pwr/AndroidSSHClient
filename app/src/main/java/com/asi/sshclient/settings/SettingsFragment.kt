package com.asi.sshclient.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asi.sshclient.MainActivity
import com.asi.sshclient.MyApplication
import com.asi.sshclient.R
import com.asi.sshclient.auth.AuthFragment
import com.asi.sshclient.auth.AuthListActivity
import kotlinx.android.synthetic.main.settings_fragment.*
import java.lang.Exception
import javax.inject.Inject

const val AUTH_PASS_INDEX = "server"

class SettingsFragment : Fragment() {

    private val CHOOSE_SERVER = 1

    @Inject
    lateinit var viewModel: SettingsViewModel
    private var listener : SettingsFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseServer.setOnClickListener { startAuthList() }
        addLogin.setOnClickListener { switchFragment() }
        addShortcut.setOnClickListener { addShortcut() }
    }

    private fun switchFragment() {
        listener?.openLoginAddPage()
    }

    private fun addShortcut() {
        //TODO redirect to shorcut fragment when addShortcut functionality is compeleted
    }

    private fun startAuthList() {
        val intent = Intent(activity, AuthListActivity::class.java)
        startActivityForResult(intent, CHOOSE_SERVER);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CHOOSE_SERVER -> viewModel.changeCurrentServer(
                data?.getIntExtra(AUTH_PASS_INDEX, 0) ?: 0
            )
            else -> throw Exception()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SettingsFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement SettingsFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface SettingsFragmentListener{
        fun openLoginAddPage()
    }
}
