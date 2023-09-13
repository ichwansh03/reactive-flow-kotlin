package com.ichwan.react.rxkotlin.flows

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ichwan.react.rxkotlin.R
import com.ichwan.react.rxkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.flowOn

class FlowsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.countLiveData.observe(this, Observer { count ->
            binding.txcount.text = "Counter: $count"
        })

    }
}