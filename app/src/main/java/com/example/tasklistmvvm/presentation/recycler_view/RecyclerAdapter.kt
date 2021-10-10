package com.example.tasklistmvvm.presentation.recycler_view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklistmvvm.data.room.TaskEntity
import com.example.tasklistmvvm.databinding.TaskrecyclerItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerAdapter(val listener: RowClickListener?) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var items = ArrayList<TaskEntity>()

    fun setDataList(data: ArrayList<TaskEntity>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskrecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                listener?.onItemClickListener(
                    items[position], true, items[position].id,
                    items[position].title, items[position].text, items[position].priority
                )
            }
        }
        holder.bind(items[position])
        Log.e(
            "Smt", "${
                items.forEach {
                    Log.e("Smt", "$it")
                }
            }}")
    }

    inner class ViewHolder(val binding: TaskrecyclerItemBinding, val listener: RowClickListener?) :
        RecyclerView.ViewHolder(binding.root) {

        val title = binding.task
        val priority = binding.circle
        val trashCan = binding.trashCan

        fun bind(data: TaskEntity) {
            title.text = data.title

            if (data.priority == true) {
                priority.visibility = View.VISIBLE
            } else {
                priority.visibility = View.GONE
            }
            trashCan.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    listener?.onDeleteTaskClickListener(data)
                }
            }

        }

    }


    interface RowClickListener {
        suspend fun onDeleteTaskClickListener(taskEntity: TaskEntity)
        suspend fun onItemClickListener(
            taskEntity: TaskEntity,
            fromRecyclerView: Boolean?,
            id: Int?,
            title: String?,
            desciption: String?,
            priority: Boolean?
        )
    }
}