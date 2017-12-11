package com.nonggun.mediq.models

data class User(var firstName: String = "",
                var lastName: String = "",
                var citizenId: String = "",
                var phoneNumber: String = "",
                var password: String = "")