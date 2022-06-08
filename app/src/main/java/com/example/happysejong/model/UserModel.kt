package com.example.happysejong.model

import android.os.Parcel
import android.os.Parcelable

data class UserModel (
    val nickName: String = "",
    val dormitory: String = "",
    val uid: String = "",
    val reports: Long = 0
        ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong()) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nickName)
        parcel.writeString(dormitory)
        parcel.writeString(uid)
        parcel.writeLong(reports)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}