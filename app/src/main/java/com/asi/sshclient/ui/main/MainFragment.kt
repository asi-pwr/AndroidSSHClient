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

        val opArray = mapOf<View, String>(add to "+", subtract to "-", divide to "/",
            multiply to "*", sqrt to "√", power to "^")

        val numArray = arrayOf<View>(number0, number1, number2, number3, number4, number5,
            number6, number7, number8, number9)

        for(i in 0..9)
            numArray[i].setOnClickListener{ viewModel.onNumberClick(i) }

        for (operation in opArray)
            operation.key.setOnClickListener{ viewModel.onOperationClick(operation.value) }

        decimalPoint.setOnClickListener { viewModel.onDecimalPointClick() }

        equals.setOnClickListener { viewModel.onEqualsClick() }

        delete.setOnClickListener { viewModel.onClearClick() }

        delete.setOnLongClickListener { viewModel.onLongCLearClick() }

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
