package com.example.todolist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapters.ToDoListAdapter
import com.example.todolist.vm.ToDoListViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration

class ToDoListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toDoListadapter: ToDoListAdapter

    private lateinit var viewModel: ToDoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.to_do_list, container, false)
        // create a adapter for recycler view
        viewModel = ViewModelProvider(requireActivity()).get(ToDoListViewModel::class.java)
        recyclerView = view.findViewById(R.id.recycler_view)
        toDoListadapter = ToDoListAdapter(
            viewModel.list.value,
            onItemClick = { item ->
                val fragment = EditItemFragment().apply {
                    arguments = EditItemBundleBuilder.build(item.id)
                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            onCheckBoxClick = { item ->
                viewModel.toggleComplete(item.id)
            })
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = toDoListadapter
        }

        viewModel.list.observe(viewLifecycleOwner) { list ->
            toDoListadapter.updateItems(list)
        }

        val addButton = view.findViewById<Button>(R.id.add_item)
        addButton.setOnClickListener {
            val input = EditText(requireContext())
            AlertDialog.Builder(requireContext())
                .setTitle("New To-Do Item")
                .setView(input)
                .setPositiveButton("Add") { _, _ ->
                    val text = input.text.toString()
                    if (text.isNotBlank()) {
                        viewModel.addItem(text)
                    } else {
                        Toast.makeText(requireContext(), "Please enter text", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
        return view
    }
}