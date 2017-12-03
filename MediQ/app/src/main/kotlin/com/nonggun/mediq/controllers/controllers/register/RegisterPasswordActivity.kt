package com.nonggun.mediq.controllers.controllers.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.nonggun.mediq.R
import kotlinx.android.synthetic.main.activity_register_password.*

class RegisterPasswordActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_password)
        supportActionBar?.hide()

        showPasswordCheckBox.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.showPasswordCheckBox -> setPasswordEditTextInputType(showPasswordCheckBox.isChecked)
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
}
