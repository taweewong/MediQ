package com.nonggun.mediq.controllers.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.facades.RegisterFacade
import com.nonggun.mediq.models.User
import com.nonggun.mediq.models.User.Key.USER_PARCEL_KEY
import com.nonggun.mediq.services.VerifyService
import kotlinx.android.synthetic.main.activity_register_profile.*

class RegisterProfileActivity : BaseActivity(), View.OnClickListener, VerifyService.OnVerifyRegisterDataComplete {
    private val registerUser = User()
    private var isPhoneNumberAvailable = false
    private var isCitizenIdAvailable = false

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
                validateInput(registerUser)
            }
        }
    }

    override fun onVerifyPhoneNumberSuccess(isValid: Boolean) {
        isPhoneNumberAvailable = isValid
        verifyDuplicate(isPhoneNumberAvailable, isCitizenIdAvailable, registerUser)
    }

    override fun onVerifyCitizenIdSuccess(isValid: Boolean) {
        isCitizenIdAvailable = isValid
        verifyDuplicate(isPhoneNumberAvailable, isCitizenIdAvailable, registerUser)
    }

    override fun onVerifyPhoneNumberFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onVerifyCitizenIdFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateInput(user: User) {
        if (isPhoneNumberValid(user.phoneNumber) and isCitizenIdValid(user.citizenId)) {
            RegisterFacade.verifyDuplicateRegisterInput(user.phoneNumber, user.citizenId, this)
        }

        updatedUi(isPhoneNumberValid(user.phoneNumber),
                isCitizenIdValid(user.citizenId),
                getString(R.string.error_empty_phone_number),
                getString(R.string.error_empty_citizen_id))
    }

    private fun verifyDuplicate(isPhoneNumberAvailable: Boolean, isCitizenIdAvailable: Boolean, user: User) {
        if (isPhoneNumberAvailable and isCitizenIdAvailable) {
            sendUserDataToRegisterNameActivity(user)
        }

        updatedUi(isPhoneNumberAvailable,
                isCitizenIdAvailable,
                getString(R.string.error_duplicate_phone_number),
                getString(R.string.error_duplicate_citizen_id))
    }

    private fun updatedUi(isPhoneNumberValid: Boolean, isCitizenIdValid: Boolean,
                          phoneNumberError: String, citizenIdError: String) {
        phoneNumberErrorText.text = phoneNumberError
        citizenIdErrorText.text = citizenIdError

        when (isPhoneNumberValid) {
            true -> phoneNumberErrorText.visibility = View.GONE
            false -> phoneNumberErrorText.visibility = View.VISIBLE
        }

        when (isCitizenIdValid) {
            true -> citizenIdErrorText.visibility = View.GONE
            false -> citizenIdErrorText.visibility = View.VISIBLE
        }
    }

    private fun sendUserDataToRegisterNameActivity(user: User) {
        val intent = Intent(this, RegisterNameActivity().javaClass)
        intent.putExtra(USER_PARCEL_KEY, user)
        startActivity(intent)
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return !phoneNumber.isEmpty()
    }

    private fun isCitizenIdValid(citizenId: String): Boolean {
        return !citizenId.isEmpty()
    }
}
