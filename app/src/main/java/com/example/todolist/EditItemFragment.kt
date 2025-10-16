package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todolist.vm.ToDoListViewModel
import kotlinx.coroutines.launch
import android.widget.Toast

class EditItemFragment : Fragment() {
    private lateinit var viewModel: ToDoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemId = EditItemBundleBuilder.getItemId(arguments)
        val view = inflater.inflate(R.layout.edit_item, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ToDoListViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            val title = viewModel.getItemById(itemId)
            val currentTitle = view.findViewById<TextView>(R.id.current_item_name)
            currentTitle.text = title
        }

        val button = view.findViewById<Button>(R.id.submit)
        val editText = view.findViewById<EditText>(R.id.edit_text)
        button.setOnClickListener {
            if (!editText.text.isNullOrBlank()) {
                viewModel.updateTitleAtId(itemId, editText.text.toString())
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Enter Text",
                    Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}