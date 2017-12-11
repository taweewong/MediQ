package com.nonggun.mediq.services

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nonggun.mediq.R
import com.nonggun.mediq.models.User

object LoginService {

    interface OnLoginComplete {
        fun onLoginPassed()
        fun onLoginFailed(message: String)
    }

    private val CHILD_USERS = "users"
    private val CHILD_PHONE_NUMBER = "phoneNumber"

    private val databaseRef = FirebaseDatabase.getInstance().reference.child(CHILD_USERS)

    fun login(context: Context, phoneNumber: String, password: String, listener: OnLoginComplete) {
        databaseRef.orderByChild(CHILD_PHONE_NUMBER).equalTo(phoneNumber).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.children.count() != 0) {
                    for (userSnapshot: DataSnapshot in dataSnapshot.children) {
                        verifyLoginInput(context, userSnapshot.getValue(User::class.java), password, listener)
                    }
                } else {
                    listener.onLoginFailed(context.getString(R.string.error_not_found_phone_number))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                listener.onLoginFailed(databaseError.message)
            }
        })
    }

    private fun verifyLoginInput(context: Context, user: User?, password: String, listener: OnLoginComplete) {
        if (user != null) {
            when (user.password == password) {
                true -> listener.onLoginPassed()
                false -> listener.onLoginFailed(context.getString(R.string.error_wrong_password))
            }
        } else {
            listener.onLoginFailed(context.getString(R.string.error_can_not_verify_login))
        }
    }
}