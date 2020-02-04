package com.asi.sshclient.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.asi.sshclient.MyApplication
import com.asi.sshclient.R
import com.asi.sshclient.databinding.FragmentAuthBinding
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class AuthFragment : Fragment() {
    @Inject
    lateinit var viewModel: AuthViewModel
    private var listener: AuthFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submit.setOnClickListener { viewModel.submit() }
        back.setOnClickListener { backToPreviousFragment() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAuthBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    fun backToPreviousFragment() {
        val valListener = listener
        if(valListener != null) valListener.backToPreviousFragment()
        else viewModel.signalFatalError()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AuthFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement AuthFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface AuthFragmentListener {
        fun backToPreviousFragment()
    }
}
