package com.nonggun.mediq.controllers.queue

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.dialogs.ReachQueueDialog
import com.nonggun.mediq.facades.ClientQueueFacade
import com.nonggun.mediq.models.User
import com.nonggun.mediq.models.User.Key.USER_PARCEL_KEY
import com.nonggun.mediq.services.ClientInQueueService.OnGetUserQueueDataListener
import com.nonggun.mediq.services.ClientInQueueService.OnGetUserQueueListener
import kotlinx.android.synthetic.main.activity_in_queue.*

class InQueueActivity : BaseActivity(), OnGetUserQueueDataListener, OnGetUserQueueListener{

    private lateinit var user: User
    private lateinit var dialog: ReachQueueDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_queue)
        supportActionBar?.hide()

        dialog = ReachQueueDialog(this)
        user = intent.getParcelableExtra(USER_PARCEL_KEY)
        ClientQueueFacade.getInQueueData(this, user, this, this)
    }

    override fun onGetCurrentQueueSuccess(user: User, userQueue: Int) {
        userQueueText.text = userQueue.toString()
    }

    override fun onGetCurrentQueueFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetCurrentQueueNotFound(user: User) {
        Log.d("DEBUG", "Not Found")
        dialog.dismiss()
        startQueueActivity(user)
    }

    override fun onGetCurrentInProgressQueueSuccess(queueNumber: String) {
        currentQueueText.text = queueNumber
    }

    override fun onGetCurrentInProgressQueueFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetWaitQueueNumberAndTimeSuccess(previousQueueNumber: Int, waitTime: String) {
        if (previousQueueNumber == 0) {
            Log.d("DEBUG", "wait time " + waitTime)
            dialog.show()
            //startQueueActivity(user)
        } else {
            waitQueueText.text = previousQueueNumber.toString()
            waitTimeText.text = waitTime
        }
    }

    override fun onGetWaitQueueNumberAndTimeFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startQueueActivity(user: User) {
        val intent = Intent(this, QueueActivity::class.java)
        intent.putExtra(USER_PARCEL_KEY, user)
        startActivity(intent)
        finish()
    }
}
