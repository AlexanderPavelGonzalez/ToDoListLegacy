package com.example.todolist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.viewdata.ToDoItem
import kotlinx.coroutines.launch
import kotlin.text.orEmpty

class ToDoListViewModel : ViewModel() {

    private val _list = MutableLiveData(
        listOf(
            ToDoItem(
                id = "1",
                title = "Clean my room",
                false
            ),
            ToDoItem(
                id = "2",
                title = "Take dog for walk",
                false
            ),
            ToDoItem(
                id = "3",
                title = "Take dog for walk",
                true
            )
        )
    )
    val list: LiveData<List<ToDoItem>> = _list

    fun toggleComplete(id: String) {
        viewModelScope.launch {
            _list.value = _list.value?.map {
                if (it.id == id) it.copy(isComplete = !it.isComplete) else it
            }
        }
    }

    suspend fun getItemById(id: String?): String? {
        return _list.value.find {it.id == id}?.title
    }

    fun updateTitleAtId(id: String?, newTitle: String) {
        viewModelScope.launch {
            _list.value = _list.value?.map {
                if (it.id == id) it.copy(title = newTitle) else it
            }
        }
    }

    fun addItem(title: String) {
        val newId = (_list.value?.size?.plus(1) ?: 1).toString()
        val newItem = ToDoItem(id = newId, title = title, isComplete = false)
        _list.value = _list.value.orEmpty() + newItem    }

    // operations to come
}