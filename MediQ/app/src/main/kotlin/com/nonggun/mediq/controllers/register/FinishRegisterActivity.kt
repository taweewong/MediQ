package com.nonggun.mediq.controllers.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.controllers.login.LoginActivity
import kotlinx.android.synthetic.main.activity_finish_register.*

class FinishRegisterActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_register)
        supportActionBar?.hide()

        backToLoginActivityButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        backToLoginActivity()
    }

    private fun backToLoginActivity() {
        val intent = Intent(this, LoginActivity().javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToLoginActivity()
    }
}
