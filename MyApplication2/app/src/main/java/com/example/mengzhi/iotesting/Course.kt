package com.example.mengzhi.iotesting

import android.os.Parcel
import android.os.Parcelable

class Course() :Parcelable {
    var name: String? = null
    var score = 0
    var studentList : ArrayList<String>? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        score = parcel.readInt()
        studentList = parcel.createStringArrayList()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.name)
        dest.writeInt(this.score)
        dest.writeStringList(studentList)
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}