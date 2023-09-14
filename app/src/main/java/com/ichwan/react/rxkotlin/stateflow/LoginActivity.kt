package com.ichwan.react.rxkotlin.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar

import com.ichwan.react.rxkotlin.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: StateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.usernameInput.text.toString(),
                binding.passwordInput.text.toString()
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUiState.collect {
                    when(it){
                        is StateViewModel.LoginUiState.Success -> {
                            Snackbar.make(binding.root, "Successfully logged in", Snackbar.LENGTH_LONG).show()
                            binding.progressCircular.isVisible = false
                        }
                        is StateViewModel.LoginUiState.Error -> {
                            Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                            binding.progressCircular.isVisible = false
                        }
                        is StateViewModel.LoginUiState.Loading -> {
                            binding.progressCircular.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}