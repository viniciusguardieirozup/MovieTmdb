package com.example.movietmdb

import android.view.View

abstract class DebouncedOnClickListener : View.OnClickListener {

    fun resetAtribute(v: View) {
        v.isClickable = true
    }

    abstract fun onDebouncedClick(v: View)

    override fun onClick(v: View?) {
        v?.let {
            v.isClickable = false
            onDebouncedClick(it)
        }
    }
}

