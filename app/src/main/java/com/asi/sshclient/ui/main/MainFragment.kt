package com.asi.sshclient.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.asi.sshclient.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: ShellViewModel

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
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = s?.trim()
                when (text?.length) {
                    (0) -> viewModel.removeSign()
                    else -> viewModel.writeSign(text.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                shellEditText.removeTextChangedListener(this)
                shellEditText.setText(" ")
                shellEditText.addTextChangedListener(this)
                shellEditText.setSelection(shellEditText.text.length)
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
        if(!::viewModel.isInitialized){
            viewModel = ViewModelProviders.of(this).get(ShellViewModel::class.java)

            viewModel.shellLiveData.observe(this, Observer { t -> shell.text = t})
        }
    }
}
