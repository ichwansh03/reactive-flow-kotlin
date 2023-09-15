package com.ichwan.react.rxkotlin.rxjava.di

import com.ichwan.react.rxkotlin.rxjava.data.RemoteDataSource
import com.ichwan.react.rxkotlin.rxjava.data.RetroClient
import com.ichwan.react.rxkotlin.rxjava.data.UserViewModel

object ServiceLocator {

    fun getRetrofitClient() = RetroClient()
    fun getRemoteDataSource() = RemoteDataSource(getRetrofitClient().instance)

    fun getUserViewModel() = UserViewModel.instance(getRemoteDataSource())
}