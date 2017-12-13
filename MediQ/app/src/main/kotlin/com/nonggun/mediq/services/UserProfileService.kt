package com.nonggun.mediq.services

import com.google.firebase.database.FirebaseDatabase
import com.nonggun.mediq.models.User

object UserProfileService {
    private val CHILD_USERS = "users"

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_USERS)

    fun editUserProfile(user: User) {
        databaseRef.child(user.userId).setValue(user)
    }
}
