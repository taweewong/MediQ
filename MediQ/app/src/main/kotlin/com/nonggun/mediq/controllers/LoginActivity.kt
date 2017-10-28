package com.nonggun.mediq.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nonggun.mediq.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
    }
}
