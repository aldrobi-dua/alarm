package com.example.alarm.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class NewTaskData(
    var id: String? = null,
    var selectedDate: String? = null,
    var selectedTime: Date = Calendar.getInstance().time,
    var ringtonePath: String? = null,
    var selectedReminder: String? = null,
    var description: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        Date(parcel.readLong()), // Convert Long to Date when reading from Parcel
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(selectedDate)
        parcel.writeLong(selectedTime.time) // Convert Date to Long when writing to Parcel
        parcel.writeString(ringtonePath)
        parcel.writeString(selectedReminder)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewTaskData> {
        override fun createFromParcel(parcel: Parcel): NewTaskData {
            return NewTaskData(parcel)
        }

        override fun newArray(size: Int): Array<NewTaskData?> {
            return arrayOfNulls(size)
        }
    }
}
