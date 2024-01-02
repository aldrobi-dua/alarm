package com.example.alarm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class Favorite : AppCompatActivity() {

    private lateinit var favoriteButton: ImageButton
    private lateinit var deleteButton: Button
    private lateinit var editButton: Button
    private lateinit var kaydetButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoriteButton = findViewById(R.id.favoriteButton)
        deleteButton = findViewById(R.id.deleteTask)
        editButton = findViewById(R.id.editTask)
        kaydetButton = findViewById(R.id.kaydet)

        favoriteButton.setOnClickListener {
            // Your code to favorite the activity
        }

        deleteButton.setOnClickListener {
            // Your code to delete the activity
        }

        editButton.setOnClickListener {
            // Your code to edit the activity
        }

        kaydetButton.setOnClickListener {
            val intent = Intent(this@Favorite, AddNewTaskActivity::class.java)
            startActivityForResult(intent, 123)
        }

    }

    // Override this method to handle the result from AddNewTaskActivity
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            // Handle the result here, for example:
            val resultData = data?.getStringExtra("resultKey")
            val selectedDate = data?.getStringExtra("selectedDate")
            val selectedTime = data?.getLongExtra("selectedTime", 0)

            // Do something with the resultData, like displaying it in a Toast
            Toast.makeText(
                this,
                "Result: $resultData, Date: $selectedDate, Time: $selectedTime",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
