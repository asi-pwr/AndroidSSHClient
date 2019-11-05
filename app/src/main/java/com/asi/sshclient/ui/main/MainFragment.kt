package com.asi.sshclient.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.asi.sshclient.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        number0.setOnClickListener {
            viewModel.onNumberClick(0)
        }

        number1.setOnClickListener {
            viewModel.onNumberClick(1)
        }

        number2.setOnClickListener {
            viewModel.onNumberClick(2)
        }

        number3.setOnClickListener {
            viewModel.onNumberClick(3)
        }

        number4.setOnClickListener {
            viewModel.onNumberClick(4)
        }

        number5.setOnClickListener {
            viewModel.onNumberClick(5)
        }

        number6.setOnClickListener {
            viewModel.onNumberClick(6)
        }

        number7.setOnClickListener {
            viewModel.onNumberClick(7)
        }

        number8.setOnClickListener {
            viewModel.onNumberClick(8)
        }

        number9.setOnClickListener {
            viewModel.onNumberClick(9)
        }

        decimalPoint.setOnClickListener {
            viewModel.onDecimalPointClick()
        }

        add.setOnClickListener {
            viewModel.onOperationClick("+")
        }

        subtract.setOnClickListener {
            viewModel.onOperationClick("-")
        }

        divide.setOnClickListener {
            viewModel.onOperationClick("/")
        }

        multiply.setOnClickListener {
            viewModel.onOperationClick("*")
        }

        sqrt.setOnClickListener {
            viewModel.onOperationClick("√")
        }

        power.setOnClickListener {
            viewModel.onOperationClick("^")
        }

        equals.setOnClickListener {
            viewModel.onEqualsClick()
        }

        delete.setOnClickListener {
            viewModel.onClearClick()
        }

        delete.setOnLongClickListener {
            viewModel.onLongCLearClick()
        }

        history.movementMethod = ScrollingMovementMethod()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(!::viewModel.isInitialized){
            viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            viewModel.inLineHistory.observe(this, Observer<String> { t -> formula.text = t })
            viewModel.history.observe(this, Observer<String> { t -> history.text = t })
        }

    }

}
