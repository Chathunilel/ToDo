package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.TaskLayoutBinding
import com.example.todo.fragments.HomeFragment
import com.example.todo.fragments.HomeFragmentDirections
import com.example.todo.model.Task

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder (val itemBinding: TaskLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Task>(){
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean{
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean  {
            return oldItem.id == newItem.id &&
                    oldItem.taskDeadline == newItem.taskDeadline &&
                    oldItem.taskDescription == newItem.taskDescription &&
                    oldItem.taskPriority == newItem.taskPriority &&
                    oldItem.taskTitle == newItem.taskTitle


        }
    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = differ.currentList[position]

        holder.itemBinding.taskTitle.text = currentTask.taskTitle
        holder.itemBinding.taskDesc.text = currentTask.taskDescription
        holder.itemBinding.taskPrio.text = currentTask.taskPriority
        holder.itemBinding.taskDeadline.text = currentTask.taskDeadline.toString()
        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(currentTask)
            it.findNavController().navigate(direction)
        }
    }

}