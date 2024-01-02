package com.example.alarm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.databinding.GorevekleBinding
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {

    private lateinit var binding: GorevekleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GorevekleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addTaskButton = binding.addTaskButton
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddNewTaskActivity::class.java)
            startActivity(intent)
        }
        FirebaseApp.initializeApp(this)

    }
}

