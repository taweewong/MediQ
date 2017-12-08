package com.nonggun.mediq.models

import android.os.Parcel
import android.os.Parcelable

data class Queue(var queueNumber: String,
                 var name: String,
                 var type: String) : Parcelable {

    enum class queueType {
        APPLICATION , WALK_IN
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(queueNumber)
        parcel.writeString(name)
        parcel.writeString(type)
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