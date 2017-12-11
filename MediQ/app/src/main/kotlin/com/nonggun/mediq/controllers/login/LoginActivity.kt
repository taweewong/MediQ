package com.nonggun.mediq.controllers.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nonggun.mediq.R
import com.nonggun.mediq.controllers.register.RegisterProfileActivity
import com.nonggun.mediq.models.User
import com.nonggun.mediq.services.RegisterService
import com.nonggun.mediq.services.VerifyService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        registerText.setOnClickListener(this)

        val registerService = VerifyService()
        registerService.verifyPhoneNumber("c")
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.registerText -> openActivity(RegisterProfileActivity().javaClass)
        }
    }

    private fun <T>openActivity(javaClass: Class<T>) {
        startActivity(Intent(this, javaClass))
    }
}
