package com.nonggun.mediq.facades

import android.content.Context
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.mediq.R
import com.nonggun.mediq.models.User
import com.nonggun.mediq.services.ClientInQueueService
import com.nonggun.mediq.services.ClientInQueueService.OnGetUserQueueDataListener
import com.nonggun.mediq.services.ClientInQueueService.OnGetUserQueueListener
import com.nonggun.mediq.services.ClientQueueService
import com.nonggun.mediq.services.ClientQueueService.OnGetQueueDataListener

object ClientQueueFacade {

    private val CHILD_QUEUES = "queues"

    fun getQueueData(context: Context, onGetQueueDataListener: OnGetQueueDataListener) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)

        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                ClientQueueService.getAvailableQueueNumber(context, onGetQueueDataListener)
                ClientQueueService.getCurrentInProgressQueue(context, onGetQueueDataListener)
                ClientQueueService.getPreviousQueueNumber(context, onGetQueueDataListener)
                ClientQueueService.getWaitTime(context, onGetQueueDataListener)
            }

            override fun onCancelled(p0: DatabaseError?) {
                Toast.makeText(context, context.getString(R.string.error_load_data), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getInQueueData(context: Context, user: User, onGetUserQueueListener: OnGetUserQueueListener,
                       onGetUserQueueDataListener: OnGetUserQueueDataListener) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)

        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                ClientInQueueService.getUserQueue(user, onGetUserQueueListener)
                ClientInQueueService.getCurrentInProgressQueue(context, onGetUserQueueDataListener)
                ClientInQueueService.getWaitQueueNumberAndTime(user.userId, onGetUserQueueDataListener)
            }

            override fun onCancelled(p0: DatabaseError?) {
                Toast.makeText(context, context.getString(R.string.error_load_data), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addQueue(user: User, queueCallback: () -> Unit) {
        ClientQueueService.addQueue(user, queueCallback)
    }

    fun removeQueue(userId: String) {
        ClientInQueueService.removeQueue(userId)
    }
}