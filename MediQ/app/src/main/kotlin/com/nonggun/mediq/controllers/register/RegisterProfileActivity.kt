package com.nonggun.mediq.controllers.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.models.User
import com.nonggun.mediq.models.User.Key.USER_PARCEL_KEY
import kotlinx.android.synthetic.main.activity_register_profile.*

class RegisterProfileActivity : BaseActivity(), View.OnClickListener {
    private val registerUser = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_profile)
        supportActionBar?.hide()

        registerProfileNextButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.registerProfileNextButton -> {
                registerUser.citizenId = registerCitizenIdEditText.text.toString()
                registerUser.phoneNumber = registerPhoneNumberEditText.text.toString()
                sendUserDataToRegisterNameActivity(registerUser)
            }
        }
    }

    private fun sendUserDataToRegisterNameActivity(user: User) {
        val intent = Intent(this, RegisterNameActivity().javaClass)
        intent.putExtra(USER_PARCEL_KEY, user)
        startActivity(intent)
    }
}
