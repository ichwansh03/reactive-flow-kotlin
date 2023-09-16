package com.ichwan.react.rxkotlin.flows

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ichwan.react.rxkotlin.databinding.ActivityMainBinding

class FlowsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.countLiveData.observe(this) { count ->
            binding.txcount.text = "Counter Normal Flow: $count"
        }

    }
}