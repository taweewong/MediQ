package com.nonggun.mediq.services

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.mediq.R
import com.nonggun.mediq.models.Queue
import com.nonggun.mediq.models.Queue.queueType.APPLICATION

object ClientQueueService {

    interface OnGetQueueDataListener {
        fun onGetPreviousQueueSuccess(previousQueueNumber: Int)
        fun onGetPreviousQueueFailed(message: String)

        fun onGetCurrentInProgressQueueSuccess(currentInProgressQueue: String)
        fun onGetCurrentInProgressQueueFailed(message: String)

        fun onGetWaitTimeSuccess(waitTime: String)
        fun onGetWaitTimeFailed(message: String)

        fun onGetAvailableQueueNumberSuccess(availableQueueNumber: Int)
        fun onGetAvailableQueueNumberFailed(message: String)
    }

    private val CHILD_QUEUES = "queues"
    private val CHILD_QUEUE_NUMBER = "queueNumber"
    private val CHILD_TYPE = "type"
    private val MAXIMUM_APPLICATION_QUEUE = 10

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)

    fun getPreviousQueueNumber(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetPreviousQueueSuccess(dataSnapshot.children.count())
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetPreviousQueueFailed(context.getString(R.string.error_load_previous_queue))
            }
        })
    }

    fun getCurrentInProgressQueue(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.limitToFirst(1).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetCurrentInProgressQueueSuccess(getCurrentQueueFrom(context, dataSnapshot))
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetCurrentInProgressQueueFailed(context.getString(R.string.error_load_in_progress_queue))
            }
        })
    }

    fun getWaitTime(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetWaitTimeSuccess(calculateWaitTime(dataSnapshot.childrenCount.toDouble()))
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetWaitTimeFailed(context.getString(R.string.error_load_wait_time))
            }
        })
    }

    fun getAvailableQueueNumber(context: Context, listener: OnGetQueueDataListener) {
        databaseRef.orderByChild(CHILD_TYPE).equalTo(APPLICATION.name).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val availableQueue = calculateRemainQueue(dataSnapshot.children.count())
                listener.onGetAvailableQueueNumberSuccess(availableQueue)
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetAvailableQueueNumberFailed(context.getString(R.string.error_load_available_queue))
            }
        })
    }

    fun addQueue(queue: Queue) {
        val queueId = databaseRef.push().key
        databaseRef.child(queueId).setValue(queue)
    }

    private fun calculateRemainQueue(applicationQueue: Int): Int {
        return MAXIMUM_APPLICATION_QUEUE - applicationQueue
    }

    private fun calculateWaitTime(queueNumber: Double): String {
        val hour = Math.floor((queueNumber * 15) / 60)
        val minute = (queueNumber * 15) % 60 / 100
        return String.format("%.2f", hour + minute)
    }

    private fun getCurrentQueueFrom(context: Context, dataSnapshot: DataSnapshot): String {
        var currentInProgressQueue = context.getString(R.string.no_previous_queue)

        for (queueSnapshot in dataSnapshot.children) {
            currentInProgressQueue = queueSnapshot.child(CHILD_QUEUE_NUMBER).getValue(Int::class.java).toString()
        }

        return currentInProgressQueue
    }
}
