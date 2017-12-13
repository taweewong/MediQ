package com.nonggun.nursemediq.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.nursemediq.model.Queue


object WalkInQueueService {

    interface OnGetAllQueueComplete {
        fun onGetAllQueueSuccess(queues: ArrayList<Queue>)
        fun onGetAllQueueFailed(message: String)
    }

    private val CHILD_QUEUES = "queues"
    private val CHILD_CURRENT_QUEUE = "currentQueue"

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

    fun addWalkInQueue(name: String, phoneNumber: String) {
        val queue = Queue()
        val queueId = databaseRef.push().key

        queue.name = name
        queue.phoneNumber = phoneNumber
        queue.queueId = queueId
        queue.type = "WALK_IN"
        getCurrentQueueNumber {
            queue.queueNumber = it
            databaseRef.child(queueId).setValue(queue)
        }

        databaseRef.child(queueId).setValue(queue)
    }

    fun deleteQueue(queue: Queue) {
        databaseRef.child(queue.queueId).removeValue()
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
}