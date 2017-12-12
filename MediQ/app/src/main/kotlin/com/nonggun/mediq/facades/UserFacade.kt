package com.nonggun.mediq.facades

import android.content.Context
import com.nonggun.mediq.models.User
import com.nonggun.mediq.services.LoginService
import com.nonggun.mediq.services.LoginService.OnLoginComplete
import com.nonggun.mediq.services.RegisterService
import com.nonggun.mediq.services.VerifyService
import com.nonggun.mediq.services.VerifyService.OnVerifyRegisterDataComplete

object UserFacade {

    fun verifyDuplicateRegisterInput(phoneNumber: String, citizenId: String, listener: OnVerifyRegisterDataComplete) {
        VerifyService.verifyPhoneNumber(phoneNumber, listener)
        VerifyService.verifyCitizenId(citizenId, listener)
    }

    fun register(user: User) {
        RegisterService.register(user)
    }

    fun login(context: Context, phoneNumber: String, password: String, listener: OnLoginComplete) {
        LoginService.login(context, phoneNumber, password, listener)
    }
}