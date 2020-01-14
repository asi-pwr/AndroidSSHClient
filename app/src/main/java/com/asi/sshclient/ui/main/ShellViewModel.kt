package com.asi.sshclient.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShellViewModel : ViewModel() {
    var command = StringBuffer()
        private set
    //'_' is used to indicate cursor position
    var shellContent = StringBuffer("_")
        private set
    var shellLiveData = MutableLiveData<String>(shellContent.toString())

    fun onEnterKey(){
        if(command.isNotBlank()) {
            command = StringBuffer()
            //TODO send command to ssh service
        }
        shellContent.insert(shellContent.length - 1, "\n")
        shellLiveData.postValue(shellContent.toString())
    }

    fun writeSign(text : String) {
        command.append(text)
        shellContent.insert(shellContent.length-1, text)
        shellLiveData.postValue(shellContent.toString())
    }

    fun removeSign() {
        if(command.isEmpty())
            return
        command.delete(command.length-1, command.length)
        shellContent.delete(shellContent.length-2, shellContent.length-1)
        shellLiveData.postValue(shellContent.toString())
    }
}
