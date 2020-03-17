package com.asi.sshclient.ui.main

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.asi.sshclient.R
import com.asi.sshclient.ssh.SshService
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*

class MainFragment : Fragment() {
    override fun onStop() {
        super.onStop()
        ssh.stop()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: ShellViewModel
    private lateinit var ssh: SshService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shellEditText.setSelection(shellEditText.text.length) //move cursor to the end

        shellEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                shellEditText.setSelection(shellEditText.text.length)
                shellWrapper.fullScroll(View.FOCUS_DOWN)

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s?.trim()
                when (text?.length) {
                    (0) -> viewModel.removeSign()
                    else -> viewModel.writeSign(text.toString())
                }
                shellWrapper.fullScroll(View.FOCUS_DOWN)
            }

            override fun afterTextChanged(s: Editable?) {
                shellEditText.removeTextChangedListener(this)
                shellEditText.setText(" ")
                shellEditText.addTextChangedListener(this)
                shellEditText.setSelection(shellEditText.text.length)
                shellWrapper.fullScroll(View.FOCUS_DOWN)
            }
        })

        shellEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onEnterKey()
            }
            true
        }
        shell.movementMethod = ScrollingMovementMethod()

        shell.setOnClickListener {
            shellEditText.requestFocus()
            val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(shellEditText, InputMethodManager.SHOW_FORCED)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!::viewModel.isInitialized) {
            viewModel = ViewModelProviders.of(this).get(ShellViewModel::class.java)

            viewModel.shellContentLiveData.observe(this, Observer { t -> shell.text = t })
        }

        ssh = SshService()

        //TODO connect on demand, now connects automatically

        GlobalScope.launch(Dispatchers.IO) {
            val message = ssh.start()
            viewModel.receiveFromShell(message)

            withContext(Dispatchers.Main) {
                viewModel.commandLiveData.observe(this@MainFragment, Observer { t ->
                    run {
                        ssh.send(t + "\r")
                    }
                })

                ssh.outputLiveData.observe(this@MainFragment, Observer { t ->
                    run {
                        viewModel.receiveFromShell(t.toString())
                        t.delete(0, t.length)
                    }
                })
            }
        }
    }
}
