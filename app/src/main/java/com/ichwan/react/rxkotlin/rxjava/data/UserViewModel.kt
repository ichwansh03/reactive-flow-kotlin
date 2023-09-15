package com.ichwan.react.rxkotlin.rxjava.data

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.math.floor

class UserViewModel(private var remoteDataSource: RemoteDataSource) : ViewModel() {

    companion object {
        fun instance(remoteDataSource: RemoteDataSource) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    return UserViewModel(remoteDataSource) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    sealed class UserUiState {
        data class User1(val data: User) : UserUiState()
        data class User2(val data: User) : UserUiState()
        data class User3(val data: User) : UserUiState()
        data class Failed(val message: String) : UserUiState()
    }

    val state = MutableLiveData<UserUiState>()

    @SuppressLint("CheckResult")
    fun getUsers(refreshStream: Observable<View>, closeStream: List<Observable<View>>) {
        val suggest1 = createSuggestionStream(closeStream[0])
        val suggest2 = createSuggestionStream(closeStream[1])
        val suggest3 = createSuggestionStream(closeStream[2])

        refreshStream
            .flatMap {
                remoteDataSource.getUsers(floor(Math.random() * 500).toInt())
            }
            .map {
                it.take(3)
            }
            .startWith(remoteDataSource.getUsers(floor(Math.random() * 500).toInt()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                state.value = UserUiState.User1(it[0])
                state.value = UserUiState.User2(it[1])
                state.value = UserUiState.User3(it[2])
            }

        suggest1.subscribe{
            state.value = UserUiState.User1(it)
        }

        suggest2.subscribe{
            state.value = UserUiState.User2(it)
        }

        suggest3.subscribe{
            state.value = UserUiState.User3(it)
        }
    }

    private fun createSuggestionStream(closeStream: Observable<View>) =
        Observable.combineLatest(
            closeStream,
            remoteDataSource.getUsers(floor(Math.random() * 500).toInt()),
            io.reactivex.rxjava3.functions.BiFunction { t1, t2 ->
                return@BiFunction t2[floor(Math.random() * t2.size).toInt()]
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}