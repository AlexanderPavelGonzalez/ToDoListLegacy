package com.example.todolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.viewdata.ToDoItem

class ToDoListAdapter(
    itemList: List<ToDoItem>?,
    val onItemClick: (ToDoItem) -> Unit,
    val onCheckBoxClick: (ToDoItem) -> Unit
) : RecyclerView.Adapter<ToDoListViewHolder>() {

    // list of to do items
    private var items: List<ToDoItem> = itemList ?: emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoListViewHolder {
        return ToDoListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.to_do_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ToDoListViewHolder,
        position: Int
    ) {
        with(holder.itemView) {
            findViewById<TextView>(R.id.toDo_title).text = items[position].title
            val checkBox = findViewById<CheckBox>(R.id.completed)
            checkBox.isChecked = items[position].isComplete
            checkBox.setOnClickListener {
                onCheckBoxClick(items[position])
            }
            setOnClickListener{
                onItemClick(items[position])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ToDoItem>) {
        items = newItems
        notifyDataSetChanged()
    }

}

class ToDoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
