package com.nonggun.mediq.services

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.mediq.R
import com.nonggun.mediq.models.Queue
import com.nonggun.mediq.models.User

object ClientInQueueService {

    interface OnGetUserQueueListener {
        fun onGetCurrentQueueSuccess(user: User, userQueue: Int)
        fun onGetCurrentQueueFailed(message: String)
        fun onGetCurrentQueueNotFound(user: User)
    }

    interface OnGetUserQueueDataListener {
        fun onGetCurrentInProgressQueueSuccess(queueNumber: String)
        fun onGetCurrentInProgressQueueFailed(message: String)

        fun onGetWaitQueueNumberAndTimeSuccess(previousQueueNumber: Int, waitTime: String)
        fun onGetWaitQueueNumberAndTimeFailed(message: String)
    }

    private val CHILD_QUEUES = "queues"
    private val CHILD_USER_ID = "userId"
    private val CHILD_QUEUE_NUMBER = "queueNumber"

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)

    fun getUserQueue(user: User, listener: OnGetUserQueueListener) {
        databaseRef.orderByChild(CHILD_USER_ID).equalTo(user.userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var userQueue: Queue? = null

                        for (userSnapshot: DataSnapshot in dataSnapshot.children) {
                            userQueue = userSnapshot.getValue(Queue::class.java)
                        }

                        getCurrentQueueFromUser(userQueue, user, listener)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        listener.onGetCurrentQueueFailed(databaseError.message)
                    }
                })
    }

    fun getCurrentInProgressQueue(context: Context, listener: OnGetUserQueueDataListener) {
        databaseRef.limitToFirst(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetCurrentInProgressQueueSuccess(getCurrentInProgressQueueFromSnapshot(context, dataSnapshot))
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetCurrentInProgressQueueFailed(context.getString(R.string.error_load_in_progress_queue))
            }
        })
    }

    fun getWaitQueueNumberAndTime(userId: String, listener: OnGetUserQueueDataListener) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val queueCounter = dataSnapshot.children
                        .mapNotNull { it.getValue(Queue::class.java) }
                        .takeWhile { it.userId != userId }
                        .count()

                listener.onGetWaitQueueNumberAndTimeSuccess(queueCounter, calculateWaitTime(queueCounter.toDouble()))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener.onGetWaitQueueNumberAndTimeFailed(databaseError.message)
            }
        })
    }

    private fun getCurrentQueueFromUser(queue: Queue?, user: User, listener: OnGetUserQueueListener) {
        if (queue != null) {
            listener.onGetCurrentQueueSuccess(user, queue.queueNumber)
        } else {
            listener.onGetCurrentQueueNotFound(user)
        }
    }

    private fun getCurrentInProgressQueueFromSnapshot(context: Context, dataSnapshot: DataSnapshot): String {
        var currentInProgressQueue = context.getString(R.string.no_previous_queue)

        for (queueSnapshot in dataSnapshot.children) {
            currentInProgressQueue = queueSnapshot.child(CHILD_QUEUE_NUMBER).getValue(Int::class.java).toString()
        }

        return currentInProgressQueue
    }

    private fun calculateWaitTime(queueNumber: Double): String {
        val hour = Math.floor((queueNumber * 15) / 60)
        val minute = (queueNumber * 15) % 60 / 100
        return String.format("%.2f", hour + minute)
    }
}