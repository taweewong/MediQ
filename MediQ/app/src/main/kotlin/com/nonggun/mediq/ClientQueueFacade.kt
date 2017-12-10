package com.nonggun.mediq

import android.content.Context
import com.nonggun.mediq.services.ClientQueueService
import com.nonggun.mediq.services.ClientQueueService.OnGetQueueDataListener

class ClientQueueFacade {

    fun getQueueData(context: Context, onGetQueueDataListener: OnGetQueueDataListener) {
        ClientQueueService.getAvailableQueueNumber(context, onGetQueueDataListener)
        ClientQueueService.getCurrentInProgressQueue(context, onGetQueueDataListener)
        ClientQueueService.getPreviousQueueNumber(context, onGetQueueDataListener)
        ClientQueueService.getWaitTime(context, onGetQueueDataListener)
    }
}