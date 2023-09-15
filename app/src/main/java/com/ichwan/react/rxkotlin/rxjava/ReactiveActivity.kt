package com.ichwan.react.rxkotlin.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ichwan.react.rxkotlin.databinding.ActivityReactiveBinding
import com.ichwan.react.rxkotlin.rxjava.data.UserViewModel
import com.ichwan.react.rxkotlin.rxjava.di.ServiceLocator
import io.reactivex.rxjava3.core.Observable

class ReactiveActivity : AppCompatActivity(), Observer<UserViewModel.UserUiState> {

    private lateinit var binding: ActivityReactiveBinding
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this, ServiceLocator.getUserViewModel())[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReactiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this, this)

        val refreshStream = Observable.create { emitter ->
            binding.buttonRefresh.setOnClickListener {
                emitter.onNext(it)
            }
        }

        val closeStream = Observable.create { emitter ->
            binding.suggest1.buttonClose.setOnClickListener {
                emitter.onNext(it)
            }
        }

        val closeStream2 = Observable.create { emitter ->
            binding.suggest2.buttonClose.setOnClickListener {
                emitter.onNext(it)
            }
        }

        val closeStream3 = Observable.create { emitter ->
            binding.suggest3.buttonClose.setOnClickListener {
                emitter.onNext(it)
            }
        }

        viewModel.getUsers(refreshStream, listOf(closeStream, closeStream2, closeStream3))
    }

    override fun onChanged(value: UserViewModel.UserUiState) {
        when(value) {
            is UserViewModel.UserUiState.User1 -> {
                with(binding.suggest1){
                    nameUser.text = value.data.username
                    Glide.with(this@ReactiveActivity).load(value.data.avatar).into(imgUser)
                }
            }
            is UserViewModel.UserUiState.User2 -> {
                with(binding.suggest3){
                    nameUser.text = value.data.username
                    Glide.with(this@ReactiveActivity).load(value.data.avatar).into(imgUser)
                }
            }
            is UserViewModel.UserUiState.User3 -> {
                with(binding.suggest3){
                    nameUser.text = value.data.username
                    Glide.with(this@ReactiveActivity).load(value.data.avatar).into(imgUser)
                }
            }
            is UserViewModel.UserUiState.Failed -> TODO()
        }
    }
}