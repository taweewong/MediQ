package com.nonggun.nursemediq.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.nursemediq.model.Queue


object QueryQueueService {

    interface OnGetAllQueueComplete {
        fun onGetAllQueueSuccess(queues: ArrayList<Queue>)
        fun onGetAllQueueFailed(message: String)
    }

    private val CHILD_QUEUES = "queues"

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)

    fun getAllQueue(listener: OnGetAllQueueComplete) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val queues = ArrayList<Queue>()
                dataSnapshot.children.mapNotNullTo(queues) { it.getValue(Queue::class.java) }

                listener.onGetAllQueueSuccess(queues)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener.onGetAllQueueFailed(databaseError.message)
            }
        })
    }
}