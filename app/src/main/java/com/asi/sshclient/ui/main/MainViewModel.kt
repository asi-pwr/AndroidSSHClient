package com.asi.sshclient.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var inLineHistory = MutableLiveData<String> ()
    var history = MutableLiveData<String> ()
    var mainModel = CalculatorService()

    fun onLongCLearClick() : Boolean {
        mainModel.clearAll()
        inLineHistory.postValue(mainModel.getInLineHistory())
        return true
    }

    fun onClearClick() {
        mainModel.clear()
        inLineHistory.postValue(mainModel.getInLineHistory())
    }

    fun onNumberClick (number : Int) {
        mainModel.writeDigit(number)
        inLineHistory.postValue(mainModel.getInLineHistory())
    }

    fun onOperationClick (operation : String) {
        mainModel.addOperator(operation)
        inLineHistory.postValue(mainModel.getInLineHistory())
    }

    fun onDecimalPointClick () {
        mainModel.writeDecimalPoint()
        inLineHistory.postValue(mainModel.getInLineHistory())
    }

    fun onEqualsClick () {
        mainModel.calculateResult()
        inLineHistory.postValue(mainModel.getInLineHistory())
        history.postValue(mainModel.getHistory())
    }
}
