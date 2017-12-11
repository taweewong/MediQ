package com.nonggun.mediq.controllers.queue

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.facade.ClientQueueFacade
import com.nonggun.mediq.services.ClientQueueService
import kotlinx.android.synthetic.main.activity_queue.*

class QueueActivity : BaseActivity(), ClientQueueService.OnGetQueueDataListener {
    private var previousQueue = ""
    private var currentInProgressQueue = ""
    private var waitTime = ""
    private var availableQueueNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)
        supportActionBar?.hide()

        ClientQueueFacade.getQueueData(this, this)
        confirmQueueText.isClickable = false
    }

    override fun onGetPreviousQueueSuccess(previousQueueNumber: String) {
        this.previousQueue = previousQueueNumber
        previousQueueText.text = previousQueueNumber
        updateLoginButton(validateQueueData())
    }

    override fun onGetCurrentInProgressQueueSuccess(currentInProgressQueue: String) {
        this.currentInProgressQueue = currentInProgressQueue
        currentQueueText.text = currentInProgressQueue
        updateLoginButton(validateQueueData())
    }

    override fun onGetWaitTimeSuccess(waitTime: String) {
        this.waitTime = waitTime
        waitTimeText.text = waitTime
        updateLoginButton(validateQueueData())
    }

    override fun onGetAvailableQueueNumberSuccess(availableQueueNumber: String) {
        this.availableQueueNumber = availableQueueNumber
        setAvailableTextColor(availableQueueNumber)
        availableQueueText.text = availableQueueNumber
        updateLoginButton(validateQueueData())
    }

    override fun onGetPreviousQueueFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetCurrentInProgressQueueFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetWaitTimeFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetAvailableQueueNumberFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateQueueData(): Boolean {
        return !previousQueue.isEmpty() and
                !currentInProgressQueue.isEmpty() and
                !waitTime.isEmpty() and
                !availableQueueNumber.isEmpty() and
                (availableQueueNumber != "0")
    }

    private fun setAvailableTextColor(availableQueueNumber: String) {
        if (availableQueueNumber == "0") {
            availableQueueText.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            availableQueueText.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        }
    }

    private fun updateLoginButton(isDataCorrect: Boolean) {
        if (isDataCorrect) {
            confirmQueueText.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            confirmQueueText.isClickable = true
        } else {
            confirmQueueText.setTextColor(ContextCompat.getColor(this, R.color.red))
            confirmQueueText.isClickable = false
        }
    }
}
