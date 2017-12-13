package com.nonggun.nursemediq.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.widget.TextView
import com.nonggun.nursemediq.R

class ConfirmDeleteDialog(activity: Activity, listener: OnClickDialogButton) {

    interface OnClickDialogButton {
        fun onClickConfirmDialog(builder: AlertDialog)
    }

    private val builder = AlertDialog.Builder(activity).create()
    private val layoutInflater = activity.layoutInflater
    private val view = layoutInflater.inflate(R.layout.dialog_delete_queue, null)
    private val confirmDeleteButton = view.findViewById<TextView>(R.id.confirmDeleteButton)
    private val cancelDeleteButton = view.findViewById<TextView>(R.id.cancelDeleteButton)

    init {
        builder.setView(view)
        confirmDeleteButton.setOnClickListener({
            listener.onClickConfirmDialog(builder)
        })
        cancelDeleteButton.setOnClickListener({
            builder.dismiss()
        })
    }

    fun show() {
        builder.show()
    }
}