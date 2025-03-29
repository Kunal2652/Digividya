

package com.example.digividya

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val taskList: MutableList<Task>,
    private val onEditClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = taskList.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskNameTextView: TextView = itemView.findViewById(R.id.task_name)
        private val taskQuantityTextView: TextView = itemView.findViewById(R.id.task_quantity)
        private val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)

        fun bind(task: Task) {
            taskNameTextView.text = task.name
            taskQuantityTextView.text = "Qty: ${task.quantity}"

            editButton.setOnClickListener { onEditClick(task) }
            deleteButton.setOnClickListener {
                taskList.remove(task)
                notifyItemRemoved(adapterPosition)
            }
        }
    }
}
