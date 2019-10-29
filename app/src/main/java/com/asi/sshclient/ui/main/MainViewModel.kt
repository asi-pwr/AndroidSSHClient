package com.asi.sshclient.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var sampleLiveData = MutableLiveData<String>()

    fun onButtonClick(){
        sampleLiveData.postValue("hello world")
    }
}
