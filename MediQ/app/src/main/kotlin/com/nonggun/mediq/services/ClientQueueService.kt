package com.nonggun.mediq.services

import com.google.firebase.database.FirebaseDatabase

class ClientQueueService {

    companion object {
        private val CHILD_QUEUES = "queues"
    }

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_QUEUES)
}
