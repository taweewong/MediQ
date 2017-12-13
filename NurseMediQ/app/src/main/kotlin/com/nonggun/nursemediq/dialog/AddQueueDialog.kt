package com.nonggun.nursemediq.dialog

import android.app.Activity
import android.app.AlertDialog
import android.widget.EditText
import android.widget.TextView
import com.nonggun.nursemediq.R

class AddQueueDialog(activity: Activity, listener: OnClickDialogButton) {

    interface OnClickDialogButton {
        fun onClickConfirmDialog(builder: AlertDialog, name: String, phoneNumber: String)
    }

    private val builder = AlertDialog.Builder(activity).create()
    private val layoutInflater = activity.layoutInflater
    private val view = layoutInflater.inflate(R.layout.dialog_add_queue, null)
    private val addQueueLabel = view.findViewById<TextView>(R.id.addQueueLabel)
    private val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
    private val phoneEditText = view.findViewById<EditText>(R.id.phonenameEditText)
    private val confirmButton = view.findViewById<TextView>(R.id.confirmButton)
    private val cancelButton = view.findViewById<TextView>(R.id.cancelButton)

    init {
        builder.setView(view)
        confirmButton.setOnClickListener({
            listener.onClickConfirmDialog(builder, nameEditText.text.toString(), phoneEditText.text.toString())
        })
        cancelButton.setOnClickListener({
            builder.dismiss()
        })
    }

    fun show() {
        builder.show()
    }
}