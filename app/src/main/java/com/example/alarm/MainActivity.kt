package com.example.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarm.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarm.data.NewTaskData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private var database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("task")
    private val taskList: MutableList<NewTaskData> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView

        val addTaskButton = binding.addTaskButton
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddNewTaskActivity::class.java)
            startActivity(intent)
        }

        firebaseDataListener()
        println("list size: ${taskList.size}")

    }

    private fun firebaseDataListener() {
        myRef.orderByChild("selectedDate").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnap ->
                    val task = dataSnap.getValue<NewTaskData>()
                    task?.let {
                        taskList.add(it)
                    }
                }
                taskList.reverse()
                setRecyclerView(taskList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun setRecyclerView(taskList: MutableList<NewTaskData>) {
        val adapter = TaskRecyclerView(
            list = taskList,
            onClickDeleteButtonListener = {
                val task = taskList.find { t ->
                    t.id == it.id
                }
                taskList.remove(task)
                it.id?.let { it1 -> myRef.child(it1).removeValue() }
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

}

