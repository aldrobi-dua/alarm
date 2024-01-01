package com.example.alarm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Favorite : AppCompatActivity() {

    private lateinit var favoriteButton: ImageButton
    private lateinit var deleteButton: Button
    private lateinit var editButton: Button
    private lateinit var kaydetButton: Button // Add this line

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoriteButton = findViewById(R.id.favoriteButton)
        deleteButton = findViewById(R.id.deleteTask)
        editButton = findViewById(R.id.editTask)
        kaydetButton = findViewById(R.id.kaydet) // Replace with the actual ID

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
            // Your code to handle the Kaydet button click
            val intent = Intent(this@Favorite, AddNewTaskActivity::class.java) // Replace with the target activity
            startActivityForResult(intent, 123) // Use startActivityForResult to get results
        }
    }

    // Override this method to handle the result from AddNewTaskActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == RESULT_OK) {
            // Handle the result here, for example:
            val resultData = data?.getStringExtra("resultKey")
            // Do something with the resultData, like displaying it in a TextView
        }
    }
}
