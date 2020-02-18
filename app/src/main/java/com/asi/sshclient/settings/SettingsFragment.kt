package com.asi.sshclient.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asi.sshclient.MyApplication
import com.asi.sshclient.R
import com.asi.sshclient.auth.AuthListActivity
import kotlinx.android.synthetic.main.settings_fragment.*
import javax.inject.Inject


class SettingsFragment : Fragment() {


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
        chooseServer.setOnClickListener { AuthListActivity.startAuthList(this) }
        addLogin.setOnClickListener { switchFragment() }
        addShortcut.setOnClickListener { addShortcut() }
    }

    private fun switchFragment() {
        listener?.openLoginAddPage()
    }

    private fun addShortcut() {
        //TODO redirect to shorcut fragment when addShortcut functionality is compeleted
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        AuthListActivity.receiveAuthIndex(viewModel::changeCurrentServer,requestCode,resultCode,data)
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
