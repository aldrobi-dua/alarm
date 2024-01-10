package com.example.alarm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

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

        /*var taskTitle = findViewById<TextView>(R.id.todoTask)
        var getData = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var stringBuilder = StringBuilder()
                for (i in snapshot.children){
                    var description = i.child("description").getValue()
                    var ringtone = i.child("ringtonePath").getValue()
                    var selectedDate = i.child("selectedDate").getValue()
                    var selectedReminder = i.child("selectedReminder").getValue()

                    taskTitle = description as TextView?
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }*/
        favoriteButton = findViewById(R.id.favoriteImageButton)
        deleteButton = findViewById(R.id.deleteImageButton)
        //editButton = findViewById(R.id.editTask)

        favoriteButton.setOnClickListener {
            // Your code to favorite the activity
        }

        deleteButton.setOnClickListener {
            // Your code to delete the activity
        }

//        editButton.setOnClickListener {
//            // Your code to edit the activity
//        }

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
