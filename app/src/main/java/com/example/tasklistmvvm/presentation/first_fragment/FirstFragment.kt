package com.example.tasklistmvvm.presentation.first_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklistmvvm.R
import com.example.tasklistmvvm.data.room.*
import com.example.tasklistmvvm.databinding.FragmentFirstBinding
import com.example.tasklistmvvm.databinding.FragmentSecondBinding
import com.example.tasklistmvvm.presentation.recycler_view.RecyclerAdapter

class FirstFragment : Fragment(R.layout.fragment_first), RecyclerAdapter.RowClickListener {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var repository: TaskRepository
    private lateinit var factory: TaskViewModelFactory
    private lateinit var binding: FragmentFirstBinding
    private lateinit var recyclerViewAdapter: RecyclerAdapter
    private lateinit var secondBinding: FragmentSecondBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        Log.e("Smt", "1")
        taskDatabase = TaskDatabase.getAppDatabase(requireContext())!!
        repository = TaskRepository(taskDatabase.getTaskDao())
        factory = TaskViewModelFactory(repository, context = requireContext())
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
        Log.e("Smt", "2")


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = RecyclerAdapter(this@FirstFragment)
            adapter = recyclerViewAdapter
            adapter = adapter
            Log.e("Smt", "$adapter")
        }

        Log.e("Smt", "3")

        viewModel.getAllTasksObservers().observe( viewLifecycleOwner, Observer {
            recyclerViewAdapter.setDataList(ArrayList(it))
            Log.e("Smt", "List = ${viewModel.getAllTasksObservers().value}")
            recyclerViewAdapter.notifyDataSetChanged()
        }

        )
        Log.e("Smt", "4")
        binding.addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }

    override suspend fun onDeleteTaskClickListener(taskEntity: TaskEntity) {
        viewModel.deleteTask(taskEntity)
    }

    override suspend fun onItemClickListener(taskEntity: TaskEntity, fromRecyclerView: Boolean?, id: Int?, title: String?, desciption: String?, priority: Boolean?) {
        val bundle = bundleOf("fromRecyclerView" to fromRecyclerView,"id" to id,  "title" to title, "description" to desciption,
            "priority" to priority)
        findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle)
    }


}