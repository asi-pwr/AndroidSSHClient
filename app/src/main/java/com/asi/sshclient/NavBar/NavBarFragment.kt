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
import com.asi.sshclient.settings.AUTH_PASS_INDEX
import java.lang.Exception


class NavBarFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private val CHOOSE_SERVER = 1

    private lateinit var viewModel: NavBarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nav_bar, container, false)
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
        when (requestCode) {
            CHOOSE_SERVER -> viewModel.changeCurrentAuth(
                data?.getIntExtra(AUTH_PASS_INDEX, 0) ?: 0
            )
            else -> throw Exception()
        }
    }

interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
