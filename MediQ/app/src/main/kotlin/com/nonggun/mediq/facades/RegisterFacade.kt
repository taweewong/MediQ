package com.nonggun.mediq.facades

import com.nonggun.mediq.services.VerifyService
import com.nonggun.mediq.services.VerifyService.OnVerifyRegisterDataComplete

object RegisterFacade {

    fun verifyDuplicateRegisterInput(phoneNumber: String, citizenId: String, listener: OnVerifyRegisterDataComplete) {
        VerifyService.verifyPhoneNumber(phoneNumber, listener)
        VerifyService.verifyCitizenId(citizenId, listener)
    }
}