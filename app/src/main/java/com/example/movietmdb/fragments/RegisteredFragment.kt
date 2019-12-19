package com.example.movietmdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movietmdb.R

class RegisteredFragment : Fragment() {
    companion object {
        fun newInstance(): RegisteredFragment {
            return RegisteredFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.movies_by_genre_layout,null)
    }
}