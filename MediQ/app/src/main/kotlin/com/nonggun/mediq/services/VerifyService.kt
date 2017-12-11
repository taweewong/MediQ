package com.nonggun.mediq.services

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object VerifyService {
    private val databaseRef = FirebaseDatabase.getInstance().reference

    private val CHILD_USERS = "users"
    private val CHILD_PHONE_NUMBER = "phoneNumber"
    private val CHILD_CITIZEN_ID = "citizenId"

    fun verifyPhoneNumber(number: String) {
        databaseRef.child(CHILD_USERS).orderByChild(CHILD_PHONE_NUMBER).equalTo(number)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.children.toMutableList().size != 0) {
                            for (phoneSnapshot: DataSnapshot in p0.children) {
                                //TODO: implemnt
                                Log.d("DEBUG SNAP", phoneSnapshot.value.toString())
                            }
                        } else {
                            Log.d("DEBUG SNAP", "null")
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("DEBUG", p0.message)
                    }
                })
    }

    fun verifyCitizenId(id: String) {
        databaseRef.child(CHILD_USERS).orderByChild(CHILD_CITIZEN_ID).equalTo(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.children.toMutableList().size != 0) {
                            for (idSnapshot: DataSnapshot in dataSnapshot.children) {
                                Log.d("DEBUG SNAP", idSnapshot.value.toString())
                            }
                        } else {
                            Log.d("DEBUG SNAP", "null")
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e("DEBUG", databaseError.message)
                    }
                })
    }
}
