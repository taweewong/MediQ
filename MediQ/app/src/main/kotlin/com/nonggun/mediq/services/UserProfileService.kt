package com.nonggun.mediq.services

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.mediq.models.User

object UserProfileService {

    interface OnGetUserComplete {
        fun onGetUserSuccess(user: User)
        fun onGetUserFailed(message: String)
    }

    private val CHILD_USERS = "users"

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_USERS)

    fun getUser(userId: String, listener: OnGetUserComplete) {
        databaseRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    listener.onGetUserSuccess(user)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener.onGetUserFailed(databaseError.message)
            }
        })
    }

    fun editUserProfile(user: User) {
        databaseRef.child(user.userId).setValue(user)
    }
}
