package com.asi.sshclient.ui.main

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class ShellEditText : EditText {

    constructor(context: Context?) : super (context)

    constructor (context: Context?, attrs: AttributeSet?) : super(context, attrs)

    @TargetApi(21)
    constructor ( context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr, 0)

    //forces cursor to last position (allows capturing del key)
    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        this.setSelection(this.text.length)
    }
}