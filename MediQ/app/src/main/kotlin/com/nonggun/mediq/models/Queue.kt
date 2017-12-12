package com.nonggun.mediq.models

import android.os.Parcel
import android.os.Parcelable

data class Queue(var queueNumber: Int,
                 var name: String,
                 var type: String,
                 var userId: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    enum class queueType {
        APPLICATION, WALK_IN
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(queueNumber)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(userId)
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