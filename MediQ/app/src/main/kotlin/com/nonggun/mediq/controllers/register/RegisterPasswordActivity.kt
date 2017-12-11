package com.nonggun.mediq.controllers.register

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.facades.RegisterFacade
import com.nonggun.mediq.models.User
import kotlinx.android.synthetic.main.activity_register_password.*

class RegisterPasswordActivity : BaseActivity(), View.OnClickListener {
    lateinit private var registerUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_password)
        supportActionBar?.hide()

        registerUser = intent.getParcelableExtra(User.Key.USER_PARCEL_KEY)
        showPasswordCheckBox.setOnClickListener(this)
        registerPasswordNextButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.showPasswordCheckBox -> setPasswordEditTextInputType(showPasswordCheckBox.isChecked)
            R.id.registerPasswordNextButton -> {
                registerUser.password = registerPasswordEditText.text.toString()
                RegisterFacade.register(registerUser)
                openFinishRegisterActivity()
            }
        }
    }

    private fun setPasswordEditTextInputType(isCheck: Boolean) {
        when (isCheck) {
            true -> {
                registerPasswordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            false -> {
                registerPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        registerPasswordEditText.setSelection(registerPasswordEditText.length())
    }

    private fun openFinishRegisterActivity() {
        val intent = Intent(this, FinishRegisterActivity().javaClass)
        startActivity(intent)
    }
}
