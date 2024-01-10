package com.example.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarm.data.NewTaskData
import com.example.alarm.databinding.ActivityReminderBinding

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newTaskData = intent.getParcelableExtra<NewTaskData>("taskData")
        binding.taskTitleTextView.text = newTaskData?.selectedDate.toString()
        binding.taskDescriptionTextView.text = newTaskData?.description.toString()

        binding.stopButton.setOnClickListener {
            MyRingtoneManager.stopRingtone()
            finish()
        }


    }
}