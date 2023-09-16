package com.ichwan.react.rxkotlin.flows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _countLiveData = MutableLiveData<Int>()
    val countLiveData: LiveData<Int> = _countLiveData

    val countDownFlow = flow<Int> {
        val beginValue = 10
        var currentValue = beginValue
        emit(beginValue)

        while (currentValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        collectFlow()
        incrementCounter()
    }

    private fun incrementCounter() {
        _stateFlow.value += 1
    }

    private fun collectFlow(){
        viewModelScope.launch {
            countDownFlow.collect { time ->
                _countLiveData.postValue(time)
            }
        }
    }
}