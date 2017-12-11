package com.nonggun.mediq.services

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerifyService {
    val database = FirebaseDatabase.getInstance()
    val databaseRef = database.reference

    fun verifyPhoneNumber(number: String){
        databaseRef.child("users").orderByChild("phoneNumuber").equalTo(number)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.children.toMutableList().size != 0) {
                            for (phoneSnapshot: DataSnapshot in p0.children){
                                Log.d("DEBUG SNAP", phoneSnapshot.value.toString())
                            }
                        } else {
                            Log.d("DEBUG SNAP", "null")
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("DEBUG", p0.message);
                    }
        })
    }

    fun verifyCitizenId(id : String){
        databaseRef.child("users").orderByChild("citenzenId").equalTo(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.children.toMutableList().size != 0) {
                            for (idSnapshot: DataSnapshot in p0.children){
                                Log.d("DEBUG SNAP", idSnapshot.value.toString())
                            }
                        } else {
                            Log.d("DEBUG SNAP", "null")
                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {
                        Log.e("DEBUG", p0.message)
                    }
    }
}