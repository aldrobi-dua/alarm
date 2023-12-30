package com.example.alarm

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.data.NewTaskData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID


class AddNewTaskActivity: AppCompatActivity() {

    private val PICK_RINGTONE_REQUEST_CODE = 1
    private var selectedDate: Calendar = Calendar.getInstance()
    private var selectedTime: Calendar = Calendar.getInstance()
    private var ringtonePath: String? = null
    private lateinit var selectedreminder: String
    private lateinit var audioButton: Button
    private lateinit var tamam: Button
    private var timePickerDialog: TimePickerDialog? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var selectedRingtoneUri: Uri? = null


    // the Values is saved in this map
    val TaskMap: MutableMap<String, NewTaskData> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_task)

        tamam = findViewById(R.id.button3)
        tamam.setOnClickListener {
            val etDescription = findViewById<EditText>(R.id.etDescription)

            val descriptionText = etDescription.text.toString()
            val reminderId =  UUID.randomUUID().toString()
            val newTaskData = NewTaskData()
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)

            newTaskData.selectedDate = formattedDate
            newTaskData.selectedTime = selectedTime.time
            newTaskData.ringtonePath = ringtonePath
            newTaskData.selectedReminder = selectedreminder
            newTaskData.description = descriptionText
            TaskMap[reminderId] = newTaskData

            Log.d("TaskMap", "TaskMap: $TaskMap")
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

        val values = arrayOf("5", "10", "15")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, values)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long) {
                selectedreminder = parent.getItemAtPosition(position).toString()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_RINGTONE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)

            selectedRingtoneUri = uri

            ringtonePath = uri?.toString()
        }
    }
}