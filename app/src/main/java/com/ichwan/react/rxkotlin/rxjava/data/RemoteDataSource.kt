package com.ichwan.react.rxkotlin.rxjava.data

import io.reactivex.rxjava3.core.Observable

class RemoteDataSource(private val api: ApiService) {

    //example of functional in rxjava
    fun getUsers(since: Int): Observable<List<User>> =
        api.getUsers(since).map { users ->
            users.map {
                User(it.id, it.login, it.avatarUrl)
            }
        }
}