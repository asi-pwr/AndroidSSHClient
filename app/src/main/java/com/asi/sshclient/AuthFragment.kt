package com.asi.sshclient

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

class AuthFragment(val authReceiver: AuthReceiver) : Fragment() {
    private var user: EditText? = null
    private var host: EditText? = null
    private var password: EditText? = null
    private var error: TextView? = null
    private var listener: OnFragmentInteractionListener? = null
    private val ERROR_MESSAGE = "Wrong data"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user = view?.findViewById(R.id.userInput)
        host = view?.findViewById(R.id.hostInput)
        password = view?.findViewById(R.id.passwordInput)
        error = view?.findViewById(R.id.passwordInput)
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    fun submit(){
        if (!authReceiver.receiveAuth(
                user.toString(),host.toString(),password.toString()))
            signalError()
    }

    fun signalError(){
        error?.text = ERROR_MESSAGE
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


}
