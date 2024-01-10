package com.example.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.widget.Toast
import com.example.alarm.data.NewTaskData

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarm Çalıyor! Hareket zamanı!", Toast.LENGTH_LONG)
            .show()

        val taskData = intent.getParcelableExtra<NewTaskData>("taskData")
        val activityIntent = Intent(context, ReminderActivity::class.java)
        activityIntent.putExtra("taskData", taskData)
        activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(activityIntent)

        MyRingtoneManager.playRingtone(context)

    }
}