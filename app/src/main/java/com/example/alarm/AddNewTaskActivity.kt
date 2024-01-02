@file:Suppress("DEPRECATION")

package com.example.alarm

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.data.NewTaskData
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddNewTaskActivity() : AppCompatActivity(), Parcelable {

    private val PICK_RINGTONE_REQUEST_CODE = 1
    private var selectedDate: Calendar = Calendar.getInstance()
    private var selectedTime: Calendar = Calendar.getInstance()
    private var ringtonePath: String? = null
    private lateinit var selectedReminder: String
    private lateinit var audioButton: Button
    private lateinit var tamam: Button
    private var timePickerDialog: TimePickerDialog? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var selectedRingtoneUri: Uri? = null

    // Values are saved in this map
    val taskMap: MutableMap<String, NewTaskData> = mutableMapOf()

    constructor(parcel: Parcel) : this() {
        ringtonePath = parcel.readString()
        selectedReminder = parcel.readString().toString()
        selectedRingtoneUri = parcel.readParcelable(Uri::class.java.classLoader)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_task)

        tamam = findViewById(R.id.kaydet)
        tamam.setOnClickListener {
            val database = FirebaseDatabase.getInstance().reference
            val tasksRef = database.child("tasks") // Create a "tasks" node in your database
            val etDescription = findViewById<EditText>(R.id.etDescription)


            val descriptionText = etDescription.text.toString()
            val reminderId = UUID.randomUUID().toString()
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)

            val newTaskData = NewTaskData()
            newTaskData.selectedDate = formattedDate
            newTaskData.selectedTime = this.selectedTime.time
            newTaskData.ringtonePath = ringtonePath
            newTaskData.selectedReminder = selectedReminder
            newTaskData.description = descriptionText

            val taskRef = tasksRef.child(reminderId)
            taskRef.setValue(newTaskData)

            Log.d("TaskMap", "TaskMap: $taskMap")

            // Set the result and finish the activity
            val resultIntent = Intent()
            resultIntent.putExtra("resultKey", "Data to pass back")
            resultIntent.putExtra("selectedDate", formattedDate)
            resultIntent.putExtra("selectedTime", selectedTime.timeInMillis)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }


        // tone button
        audioButton = findViewById(R.id.audio_button)
        audioButton.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            startActivityForResult(intent, PICK_RINGTONE_REQUEST_CODE)
        }

        // date button
        val date: Button = findViewById(R.id.date_button)
        date.setOnClickListener {
            showDatePicker()
        }
        val time: Button = findViewById(R.id.time_button)
        time.setOnClickListener {
            showTimePicker()
        }

        val spinner = findViewById<Spinner>(R.id.spinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.reminder_times,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedReminder = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun showDatePicker() {
        datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog?.show()
        datePickerDialog?.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
            dialog.dismiss()
        }
    }

    private fun showTimePicker() {
        timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
            },
            selectedTime.get(Calendar.HOUR_OF_DAY),
            selectedTime.get(Calendar.MINUTE),
            false
        )
        timePickerDialog?.show()
        timePickerDialog?.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
            dialog.dismiss()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ringtonePath)
        parcel.writeString(selectedReminder)
        parcel.writeParcelable(selectedRingtoneUri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddNewTaskActivity> {
        override fun createFromParcel(parcel: Parcel): AddNewTaskActivity {
            return AddNewTaskActivity(parcel)
        }

        override fun newArray(size: Int): Array<AddNewTaskActivity?> {
            return arrayOfNulls(size)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_RINGTONE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)

            selectedRingtoneUri = uri
            ringtonePath = uri?.toString()
        }
    }
}
