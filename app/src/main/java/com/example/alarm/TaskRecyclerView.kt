package com.example.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarm.data.NewTaskData
import com.example.alarm.databinding.FavoriteOneLayoutBinding

class TaskRecyclerView(
    private val list: List<NewTaskData>,
    private val onClickDeleteButtonListener: (NewTaskData) -> Unit
): RecyclerView.Adapter<TaskRecyclerView.TaskViewHolder>() {

    inner class TaskViewHolder(
        private val item: FavoriteOneLayoutBinding
    ): RecyclerView.ViewHolder(item.root) {
        fun bind(task: NewTaskData) {
            val title = task.selectedDate + " " + task.selectedTime
            item.taskTitleTextView.text = title
            item.taskDescriptionTextView.text = task.description
            item.deleteImageButton.setOnClickListener {
                onClickDeleteButtonListener(task)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = FavoriteOneLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}