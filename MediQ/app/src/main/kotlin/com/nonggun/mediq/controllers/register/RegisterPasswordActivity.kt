package com.nonggun.mediq.controllers.register

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.controllers.login.LoginActivity
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
                Toast.makeText(this, registerUser.toString(), Toast.LENGTH_LONG).show()

                val intent = Intent(this, LoginActivity().javaClass)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
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

    }
}
