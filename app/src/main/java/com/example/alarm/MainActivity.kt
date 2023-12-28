package com.example.alarm
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val addTaskButton = binding.addTaskButton
        addTaskButton.setOnClickListener {
            val intent =  Intent(this, AddNewTaskActivity::class.java )
            startActivity(intent)
        }
    }
}