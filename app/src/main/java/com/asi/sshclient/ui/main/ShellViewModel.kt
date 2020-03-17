package com.asi.sshclient.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShellViewModel : ViewModel() {
    private var command = StringBuffer()
    var commandLiveData = MutableLiveData<String>(command.toString())
        private set
    //'_' is used to indicate cursor position
    private var shellContent = StringBuffer("_")
    var shellContentLiveData = MutableLiveData<String>(shellContent.toString())
        private set

    fun onEnterKey() {
        commandLiveData.postValue(command.toString())
        command = StringBuffer()
        shellContent.insert(shellContent.length - 1, "\n")
        shellContentLiveData.postValue(shellContent.toString())
    }

    fun writeSign(text: String) {
        command.append(text)
        shellContent.insert(shellContent.length - 1, text)
        shellContentLiveData.postValue(shellContent.toString())
    }

    fun receiveFromShell(text: String) {
        shellContent.insert(shellContent.length - 1, text)
        shellContentLiveData.postValue(shellContent.toString())
    }

    fun removeSign() {
        if (command.isEmpty())
            return
        command.delete(command.length - 1, command.length)
        shellContent.delete(shellContent.length - 2, shellContent.length - 1)
        shellContentLiveData.postValue(shellContent.toString())
    }
}
