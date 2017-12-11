package com.nonggun.mediq.controllers.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.models.User
import com.nonggun.mediq.models.User.Key.USER_PARCEL_KEY
import kotlinx.android.synthetic.main.activity_register_name.*

class RegisterNameActivity : BaseActivity(), View.OnClickListener {
    lateinit private var registerUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_name)
        supportActionBar?.hide()

        registerUser = intent.getParcelableExtra(USER_PARCEL_KEY)
        registerNameNextButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.registerNameNextButton -> {
                registerUser.firstName = registerFirstNameEditText.text.toString()
                registerUser.lastName = registerLastNameEditText.text.toString()
                sendUserDataToRegisterPasswordActivity(registerUser)
            }
        }
    }

    private fun sendUserDataToRegisterPasswordActivity(user: User) {
        val intent = Intent(this, RegisterPasswordActivity().javaClass)
        intent.putExtra(USER_PARCEL_KEY, user)
        startActivity(intent)
    }
}
