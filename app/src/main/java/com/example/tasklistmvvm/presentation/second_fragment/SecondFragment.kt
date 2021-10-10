package com.example.tasklistmvvm.presentation.second_fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasklistmvvm.R
import com.example.tasklistmvvm.data.room.*
import com.example.tasklistmvvm.databinding.FragmentSecondBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var repository: TaskRepository
    private lateinit var factory: TaskViewModelFactory
    private lateinit var binding: FragmentSecondBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: SecondFragmentArgs by navArgs()
        taskDatabase = TaskDatabase.getAppDatabase(requireContext())!!
        binding = FragmentSecondBinding.bind(view)
        repository = TaskRepository(taskDao = taskDatabase.getTaskDao())
        factory = TaskViewModelFactory(repository, context = requireContext())
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        Log.e("Smt", "Bundle = $args ")
        if (args.fromRecyclerView) {
            binding.taskTitle.setText(args.title)
            binding.taskDescription.setText(args.description)
            binding.checkBox.isChecked = args.priority == true
            binding.saveButton.text = "Update"
        }
        Log.e("Smt", "5")

        binding.saveButton.setOnClickListener {

            val title = binding.taskTitle.text.toString()

            val text = binding.taskDescription.text.toString()

            val priority = binding.checkBox.isChecked
            if (title.isNotEmpty()) {
                val task = TaskEntity(id = null, title = title, text = text, priority = priority)
                if (args.fromRecyclerView) {
                    val task = TaskEntity(
                        args.id,
                        title, priority, text
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.e("Smt", "Task is $task")
                        viewModel.updateTaskInfo(task)
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.insertTaskInfo(entity = task)
                    }
                }


                findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill the task title field", Toast.LENGTH_SHORT).show()
            }
        }

        Log.e("Smt", "6")

    }

//    override suspend fun onDeleteTaskClickListener(taskEntity: TaskEntity) {
////        viewModel.deleteTask(taskEntity)
//    }
//
//    override suspend fun onItemClickListener(taskEntity: TaskEntity, id: Int?) {
////        binding.taskTitle.setText(taskEntity.title)
////        binding.taskDescription.setText(taskEntity.text)
////        binding.checkBox.isChecked = taskEntity.priority!!
//
//    }

}