package com.nonggun.mediq.facades

import com.nonggun.mediq.models.User
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
}