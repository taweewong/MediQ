package com.nonggun.mediq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nonggun.mediq.utilities.StatusBarUtils

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        StatusBarUtils.fullScreenLayout(window)
    }
}
