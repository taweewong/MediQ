package com.nonggun.mediq.services

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object VerifyService {

    interface OnVerifyRegisterDataComplete {
        fun onVerifyPhoneNumberSuccess(isValid: Boolean)
        fun onVerifyPhoneNumberFailed(message: String)
        fun onVerifyCitizenIdSuccess(isValid: Boolean)
        fun onVerifyCitizenIdFailed(message: String)
    }

    private val databaseRef = FirebaseDatabase.getInstance().reference

    private val CHILD_USERS = "users"
    private val CHILD_PHONE_NUMBER = "phoneNumber"
    private val CHILD_CITIZEN_ID = "citizenId"

    fun verifyPhoneNumber(number: String, listener: OnVerifyRegisterDataComplete) {
        databaseRef.child(CHILD_USERS).orderByChild(CHILD_PHONE_NUMBER).equalTo(number)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.children.toMutableList().size != 0) {
                            listener.onVerifyPhoneNumberSuccess(true)
                        } else {
                            listener.onVerifyCitizenIdSuccess(false)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        listener.onVerifyPhoneNumberFailed(databaseError.message)
                    }
                })
    }

    fun verifyCitizenId(id: String, listener: OnVerifyRegisterDataComplete) {
        databaseRef.child(CHILD_USERS).orderByChild(CHILD_CITIZEN_ID).equalTo(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.children.toMutableList().size != 0) {
                            listener.onVerifyCitizenIdSuccess(true)
                        } else {
                            listener.onVerifyCitizenIdSuccess(true)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        listener.onVerifyCitizenIdFailed(databaseError.message)
                    }
                })
    }
}
