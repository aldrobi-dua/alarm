package com.example.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.data.NewTaskData
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddNewTaskActivity: AppCompatActivity() {

    private val pickRingtoneRequestCode = 1
    private var selectedDate: Calendar = Calendar.getInstance()
    private var selectedTime: Calendar = Calendar.getInstance()
    private var ringtonePath: String? = null
    private lateinit var selectedReminder: String
    private lateinit var audioButton: Button
    private lateinit var save: Button
    private var timePickerDialog: TimePickerDialog? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var selectedRingtoneUri: Uri? = null
    private var database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("task")



    // the Values is saved in this map
    private val taskMap: MutableMap<String, NewTaskData> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_task)

        // tone button
        audioButton = findViewById(R.id.audio_button)
        audioButton.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            startActivityForResult(intent, pickRingtoneRequestCode)
        }

        // date button
        val date: Button = findViewById(R.id.date_button)
        date.setOnClickListener {
            showDatePicker()

        }

        // time button
        val time: Button = findViewById(R.id.time_button)
        time.setOnClickListener {
            showTimePicker()
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        val values = arrayOf(
            "5 dakika önce",
            "10 dakika önce",
            "15 dakika önce",
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, values)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long) {
                selectedReminder = when (position) {
                    0 -> {
                        "0"
                    }
                    1 -> {
                        "10"
                    }
                    else -> {
                        "15"
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        save = findViewById(R.id.saveButton)
        save.setOnClickListener {
            val etDescription = findViewById<EditText>(R.id.etDescription)
            val descriptionText = etDescription.text.toString()
            val reminderId =  UUID.randomUUID().toString()
            val newTaskData = NewTaskData()
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)

            newTaskData.selectedDate = formattedDate
            newTaskData.selectedTime = selectedTime.time
            newTaskData.ringtonePath = ringtonePath
            newTaskData.selectedReminder = selectedReminder
            newTaskData.description = descriptionText
            taskMap[reminderId] = newTaskData

            val taskReference = myRef.child(reminderId)
            newTaskData.id = taskReference.key
            taskReference.setValue(newTaskData)
                .addOnSuccessListener {
                    Log.d("AddNewTaskActivity", "Veri başarıyla eklendi.")
                    Toast.makeText(this, "Kaydedildi", Toast.LENGTH_SHORT).show()
                    setAlarm(newTaskData)
                    val intent = Intent(this, MainActivity::class.java )
                    startActivity(intent)

                }
                .addOnFailureListener {
                    Log.e("AddNewTaskActivity", "Veri eklenirken hata oluştu.", it)
                    Toast.makeText(this, "Kaydetme başarısız oldu.", Toast.LENGTH_SHORT).show()
                }

            Log.d("TaskMap", "TaskMap: $taskMap")

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickRingtoneRequestCode && resultCode == Activity.RESULT_OK) {
            val uri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            selectedRingtoneUri = uri
            ringtonePath = uri?.toString()
        }
    }

    private fun setAlarm(taskData: NewTaskData) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        intent.putExtra("taskData", taskData)


        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // NewTaskData içindeki zaman bilgisini kullanarak alarmı ayarlayın
        val calendar = Calendar.getInstance().apply {
            time = taskData.selectedTime
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

}