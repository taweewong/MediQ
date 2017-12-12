package com.nonggun.mediq.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.widget.TextView
import com.nonggun.mediq.R

class QueueDialog(activity: Activity, listener: OnClickQueueDialogButton) {

    interface OnClickQueueDialogButton {
        fun onClickQueueDialogPositiveButton()
    }

    private val builder: AlertDialog = AlertDialog.Builder(activity).create()
    private val layoutInflater = activity.layoutInflater
    private val view = layoutInflater.inflate(R.layout.dialog_queue, null)
    private val queueDialogTitle = view.findViewById<TextView>(R.id.queueDialogTitle)
    private val queueDialogMessage = view.findViewById<TextView>(R.id.queueDialogMessage)
    private val queueDialogPositiveText = view.findViewById<TextView>(R.id.queueDialogPositiveText)
    private val queueDialogNegativeText = view.findViewById<TextView>(R.id.queueDialogNegativeText)

    init {
        builder.setView(view)
        queueDialogPositiveText.setOnClickListener({
            listener.onClickQueueDialogPositiveButton()
        })
        queueDialogNegativeText.setOnClickListener({
            builder.dismiss()
        })
    }

    fun setTitle(text: String) {
        queueDialogTitle.text = text
    }

    fun setMessage(text: String) {
        queueDialogMessage.text = text
    }

    fun show() {
        builder.show()
    }
}
