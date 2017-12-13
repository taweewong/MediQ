package com.nonggun.mediq.services

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.mediq.R
import com.nonggun.mediq.models.Queue
import com.nonggun.mediq.models.Queue.queueType.APPLICATION
import com.nonggun.mediq.models.User

object ClientQueueService {

    interface OnGetQueueDataListener {
        fun onGetPreviousQueueSuccess(previousQueueNumber: String)
        fun onGetPreviousQueueFailed(message: String)

        fun onGetCurrentInProgressQueueSuccess(currentInProgressQueue: String)
        fun onGetCurrentInProgressQueueFailed(message: String)

        fun onGetWaitTimeSuccess(waitTime: String)
        fun onGetWaitTimeFailed(message: String)

        fun onGetAvailableQueueNumberSuccess(availableQueueNumber: String)
        fun onGetAvailableQueueNumberFailed(message: String)
    }

    private val CHILD_QUEUES = "queues"
    private val CHILD_QUEUE_NUMBER = "queueNumber"
    private val CHILD_TYPE = "type"
    private val CHILD_CURRENT_QUEUE = "currentQueue"
    private val MAXIMUM_APPLICATION_QUEUE = 10

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)

    fun getPreviousQueueNumber(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetPreviousQueueSuccess(dataSnapshot.children.count().toString())
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetPreviousQueueFailed(context.getString(R.string.error_load_previous_queue))
            }
        })
    }

    fun getCurrentInProgressQueue(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.limitToFirst(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetCurrentInProgressQueueSuccess(getCurrentInProgressQueueFromSnapshot(context, dataSnapshot))
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetCurrentInProgressQueueFailed(context.getString(R.string.error_load_in_progress_queue))
            }
        })
    }

    fun getWaitTime(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetWaitTimeSuccess(calculateWaitTime(dataSnapshot.childrenCount.toDouble()))
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetWaitTimeFailed(context.getString(R.string.error_load_wait_time))
            }
        })
    }

    fun getAvailableQueueNumber(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.orderByChild(CHILD_TYPE).equalTo(APPLICATION.name)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val availableQueue = calculateRemainAvailableQueue(dataSnapshot.children.count())
                        listener.onGetAvailableQueueNumberSuccess(availableQueue.toString())
                    }

                    override fun onCancelled(dataSnapshot: DatabaseError?) {
                        listener.onGetAvailableQueueNumberFailed(context.getString(R.string.error_load_available_queue))
                    }
                })
    }

    fun addQueue(user: User, queueCallBack: () -> Unit) {
        val queueId = databaseRef.push().key
        val queue = Queue()
        queue.name = String.format("%s %s", user.firstName, user.lastName)
        queue.userId = user.userId
        queue.type = APPLICATION.name
        queue.queueId = queueId
        queue.phoneNumber = user.phoneNumber
        getCurrentQueueNumber(callback = {
            queue.queueNumber = it
            databaseRef.child(queueId).setValue(queue)
            queueCallBack()
        })
    }

    private fun getCurrentQueueNumber(callback: (queue: Int) -> Unit) {
        val currentQueueRef = FirebaseDatabase.getInstance().reference.child(CHILD_CURRENT_QUEUE)
        currentQueueRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentQueue = dataSnapshot.getValue(Int::class.java)
                if (currentQueue != null) {
                    callback(currentQueue)
                    currentQueueRef.setValue(currentQueue + 1)
                }
            }

            override fun onCancelled(p0: DatabaseError?) {

            }

        })
    }

    private fun calculateRemainAvailableQueue(applicationQueue: Int): Int {
        return MAXIMUM_APPLICATION_QUEUE - applicationQueue
    }

    private fun calculateWaitTime(queueNumber: Double): String {
        val hour = Math.floor((queueNumber * 15) / 60)
        val minute = (queueNumber * 15) % 60 / 100
        return String.format("%.2f", hour + minute)
    }

    private fun getCurrentInProgressQueueFromSnapshot(context: Context, dataSnapshot: DataSnapshot): String {
        var currentInProgressQueue = context.getString(R.string.no_previous_queue)

        for (queueSnapshot in dataSnapshot.children) {
            currentInProgressQueue = queueSnapshot.child(CHILD_QUEUE_NUMBER).getValue(Int::class.java).toString()
        }

        return currentInProgressQueue
    }
}
