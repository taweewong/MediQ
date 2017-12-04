package com.nonggun.mediq.controllers.queue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nonggun.mediq.R

class QueueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)
        supportActionBar?.hide()
    }
}
