package com.asi.sshclient.NavBar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asi.sshclient.R
import com.asi.sshclient.auth.AuthListActivity
import kotlinx.android.synthetic.main.fragment_nav_bar.*
import java.lang.Exception
import javax.inject.Inject

class NavBarFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    lateinit var viewModel: NavBarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nav_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connect.setOnClickListener { viewModel.connect() }
        pickServer.setOnClickListener { AuthListActivity.startAuthList(this) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        AuthListActivity.receiveAuthIndex(
            viewModel::changeCurrentAuth,
            requestCode,
            resultCode,
            data
        )
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    fun pickServer() {
        //TODO
    }

}
