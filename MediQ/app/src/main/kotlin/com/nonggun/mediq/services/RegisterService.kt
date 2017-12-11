package com.nonggun.mediq.services

import com.google.firebase.database.FirebaseDatabase
import com.nonggun.mediq.models.User

object RegisterService {
    private val databaseRef = FirebaseDatabase.getInstance().reference

    private val CHILD_USERS = "users"

    fun register(user: User){
        val id = databaseRef.push().key
        databaseRef.child(CHILD_USERS).child(id).setValue(user)
    }
}

