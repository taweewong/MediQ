package com.nonggun.mediq.controllers.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.controllers.queue.QueueActivity
import com.nonggun.mediq.controllers.register.RegisterProfileActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        loginButton.setOnClickListener(this)
        registerText.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.loginButton -> openActivity(QueueActivity().javaClass)
            R.id.registerText -> openActivity(RegisterProfileActivity().javaClass)
        }
    }

    private fun <T>openActivity(javaClass: Class<T>) {
        startActivity(Intent(this, javaClass))
    }
}
