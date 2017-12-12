package com.nonggun.mediq.controllers.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.controllers.queue.QueueActivity
import com.nonggun.mediq.controllers.register.RegisterProfileActivity
import com.nonggun.mediq.facades.UserFacade
import com.nonggun.mediq.services.LoginService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener, LoginService.OnLoginComplete {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        loginButton.setOnClickListener(this)
        registerText.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.registerText -> openActivity(RegisterProfileActivity().javaClass)
            R.id.loginButton -> {
                val phoneNumber = phoneNumberEditText.text.toString()
                val password = passwordEditText.text.toString()
                //TODO: edit login layout
                UserFacade.login(this, phoneNumber, password, this)
            }
        }
    }

    override fun onLoginPassed() {
        openActivity(QueueActivity().javaClass)
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun <T>openActivity(javaClass: Class<T>) {
        startActivity(Intent(this, javaClass))
    }
}
