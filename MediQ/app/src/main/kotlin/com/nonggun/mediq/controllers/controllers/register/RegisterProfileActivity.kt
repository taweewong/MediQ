package com.nonggun.mediq.controllers.controllers.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nonggun.mediq.R
import kotlinx.android.synthetic.main.activity_register_profile.*

class RegisterProfileActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_profile)
        supportActionBar?.hide()

        registerProfileNextButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.registerProfileNextButton -> openActivity(RegisterNameActivity().javaClass)
        }
    }

    private fun <T>openActivity(javaClass: Class<T>) {
        startActivity(Intent(this, javaClass))
    }
}
