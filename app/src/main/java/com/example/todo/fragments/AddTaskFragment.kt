package com.example.todo.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.model.Task
import com.example.todo.viewmodel.TaskViewModel

class AddTaskFragment : Fragment(R.layout.fragment_add_task), MenuProvider {

    private var addTaskBinding: FragmentAddTaskBinding? = null
    private val binding get() = addTaskBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var addTaskView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel = (activity as MainActivity).taskViewModel
        addTaskView = view

        binding.addTaskPrio.setOnClickListener {
            showPriorityDialog()
        }
    }

    private fun showPriorityDialog() {
        val priorities = arrayOf("High", "Medium", "Low")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Priority")
        builder.setItems(priorities) { _, which ->
            val selectedPriority = priorities[which]
            binding.addTaskPrio.setText(selectedPriority)
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun saveTask(view: View){
        val taskTitle = binding.addTaskTitle.text.toString().trim()
        val taskDescription = binding.addTaskDesc.text.toString().trim()
        val taskPriority = binding.addTaskPrio.text.toString().trim()
        val taskDeadline = binding.addDeadline.text.toString().trim()

        if(taskTitle.isNotEmpty()){
            val task = Task(0, taskTitle, taskPriority, taskDescription, taskDeadline)
            taskViewModel.addTask(task)

            Toast.makeText(addTaskView.context, "Task Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(addTaskView.context, "Please enter task title", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveTask(addTaskView)
                true
            }
            else -> false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        addTaskBinding = null
    }
}