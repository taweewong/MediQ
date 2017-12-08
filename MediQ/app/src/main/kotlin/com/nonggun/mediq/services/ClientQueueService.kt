package com.nonggun.mediq.services

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.mediq.R

class ClientQueueService(private val context: Context) {

    interface OnGetPreviousQueueNumberListener {
        fun onGetPreviousQueueSuccess(previousQueueNumber: Int)
        fun onGetPreviousQueueFailed(message: String)
    }

    interface OnGetCurrentInProgressQueueListener {
        fun onGetCurrentInProgressQueueSuccess(currentInProgressQueue: String)
        fun onGetCurrentInProgressQueueFailed(message: String)
    }

    companion object {
        private val CHILD_QUEUES = "queues"
        private val CHILD_QUEUE = "queue"
    }

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)

    fun getPreviousQueueNumber(listener: OnGetPreviousQueueNumberListener) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetPreviousQueueSuccess(dataSnapshot.children.count())
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetPreviousQueueFailed(context.getString(R.string.error_load_previous_queue))
            }
        })
    }

    fun getCurrentInProgressQueue(listener: OnGetCurrentInProgressQueueListener) {
        databaseRef.limitToFirst(1).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onGetCurrentInProgressQueueSuccess(getCurrentQueueFrom(dataSnapshot))
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetCurrentInProgressQueueFailed(context.getString(R.string.error_load_inprogress_queue))
            }
        })
    }

    private fun getCurrentQueueFrom(dataSnapshot: DataSnapshot): String {
        var currentInProgressQueue = context.getString(R.string.no_previous_queue)

        for (queueSnapshot in dataSnapshot.children) {
            currentInProgressQueue = queueSnapshot.child(CHILD_QUEUE).getValue(String::class.java)
        }

        return currentInProgressQueue
    }
}
