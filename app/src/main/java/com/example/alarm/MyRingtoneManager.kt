package com.example.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager

object MyRingtoneManager {
    private var ringtone: Ringtone? = null

    fun playRingtone(context: Context) {
        try {
            var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
            ringtone = RingtoneManager.getRingtone(context, alarmUri)
            ringtone?.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopRingtone() {
        ringtone?.stop()
    }
}
