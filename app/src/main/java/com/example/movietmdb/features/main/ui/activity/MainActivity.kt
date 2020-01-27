package com.example.movietmdb.features.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //listener for bottom navegation
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setNavigationController()
    }

    private fun setNavigationController() {
        navController = Navigation.findNavController(this, R.id.content_changer)
        binding.bnMenu.setupWithNavController(navController)
        binding.bnMenu.setOnNavigationItemReselectedListener {  }
    }
}
