package com.nonggun.mediq.dialogs

import android.app.Activity
import android.app.AlertDialog
import com.nonggun.mediq.R

class ReachQueueDialog(activity: Activity) {
    private val builder: AlertDialog = AlertDialog.Builder(activity).create()
    private val layoutInflater = activity.layoutInflater
    private val view = layoutInflater.inflate(R.layout.dialog_reach_queue, null)
    private var isDismissed = 0

    init {
        builder.setView(view)
        builder.setCancelable(false)
    }

    fun show() {
        if (isDismissed == 0) {
            builder.show()
        }
    }

    fun dismiss() {
        builder.dismiss()
        isDismissed = 1
    }
}
