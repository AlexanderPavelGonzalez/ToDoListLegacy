package com.example.todolist

import android.os.Bundle


object EditItemBundleBuilder {
    private const val ARGS_ITEM_ID = "item_id"



    fun build(id: String): Bundle {
        return Bundle().apply {
            putString(ARGS_ITEM_ID, id)
        }
    }

    fun getItemId(bundle: Bundle?): String? {
        return bundle?.getString(ARGS_ITEM_ID)
    }

}