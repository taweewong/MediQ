package com.nonggun.mediq.controllers.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nonggun.mediq.R

class RegisterNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_name)
        supportActionBar?.hide()
    }
}
