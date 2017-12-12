package com.nonggun.mediq.models

import android.os.Parcel
import android.os.Parcelable

data class User(var firstName: String = "",
                var lastName: String = "",
                var citizenId: String = "",
                var phoneNumber: String = "",
                var password: String = "",
                var userId: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    object Key {
        val USER_PARCEL_KEY = "user"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(citizenId)
        parcel.writeString(phoneNumber)
        parcel.writeString(password)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


}