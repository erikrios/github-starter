package com.erikriosetiawan.githubstarter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.erikriosetiawan.githubstarter.databinding.ActivityMainBinding
import com.erikriosetiawan.githubstarter.repositories.UserRepository
import com.erikriosetiawan.githubstarter.ui.viewmodels.MainViewModel
import com.erikriosetiawan.githubstarter.ui.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = UserRepository()
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }
}