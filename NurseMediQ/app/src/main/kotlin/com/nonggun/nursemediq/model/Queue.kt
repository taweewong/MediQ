package com.nonggun.nursemediq.model

import android.os.Parcel
import android.os.Parcelable

data class Queue(var queueNumber: Int = -1,
                 var name: String = "",
                 var type: String = "",
                 var userId: String = "",
                 var phoneNumber: String = "",
                 var queueId: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(queueNumber)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(userId)
        parcel.writeString(phoneNumber)
        parcel.writeString(queueId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Queue> {
        override fun createFromParcel(parcel: Parcel): Queue {
            return Queue(parcel)
        }

        override fun newArray(size: Int): Array<Queue?> {
            return arrayOfNulls(size)
        }
    }

}