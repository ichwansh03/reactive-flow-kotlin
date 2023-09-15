package com.ichwan.react.rxkotlin.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ichwan.react.rxkotlin.R

class ReactiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reactive)
    }
}