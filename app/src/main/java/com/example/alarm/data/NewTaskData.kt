package com.example.alarm.data


import java.util.Calendar
import java.util.Date

data class NewTaskData(
    var selectedDate: String? = null,
    var selectedTime: Date = Calendar.getInstance().time,
    var ringtonePath: String? = null,
    var selectedReminder: String? = null,
    var description: String = ""
)
