package com.nonggun.mediq.controllers.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.WindowManager
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.controllers.login.LoginActivity
import com.nonggun.mediq.controllers.splashscreen.SplashScreenActivity
import com.nonggun.mediq.models.User
import com.nonggun.mediq.models.User.Key.USER_PARCEL_KEY
import com.nonggun.mediq.services.UserProfileService
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        user = intent.getParcelableExtra(USER_PARCEL_KEY)
        initializeStatusBar()
        initializeUi()
    }

    private fun initializeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ActivityCompat.getColor(this, R.color.bgGray)
        }
    }

    private fun initializeUi() {
        editProfileCancelButton.visibility = View.GONE
        editProfileConfirmButton.visibility = View.GONE

        editProfileButton.visibility = View.VISIBLE
        logoutButton.visibility = View.VISIBLE

        profileFirstNameEditText.setText(user.firstName)
        profileLastNameEditText.setText(user.lastName)
        profileCitizenIdEditText.setText(user.citizenId)
        profilePhoneNumberEditText.setText(user.phoneNumber)

        profileFirstNameEditText.isEnabled = false
        profileLastNameEditText.isEnabled = false
        profileCitizenIdEditText.isEnabled = false
        profilePhoneNumberEditText.isEnabled = false

        editProfileButton.setOnClickListener(this)
        editProfileConfirmButton.setOnClickListener(this)
        editProfileCancelButton.setOnClickListener(this)
        logoutButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.editProfileButton -> changeStateToEditProfile()
            R.id.editProfileCancelButton -> initializeUi()
            R.id.editProfileConfirmButton -> confirmUpdateUser()
            R.id.logoutButton -> logout()
        }
    }

    private fun changeStateToEditProfile() {
        updateEditProfileUi()
    }

    private fun updateEditProfileUi() {
        editProfileCancelButton.visibility = View.VISIBLE
        editProfileConfirmButton.visibility = View.VISIBLE

        profileFirstNameEditText.isEnabled = true
        profileLastNameEditText.isEnabled = true
        profileCitizenIdEditText.isEnabled = true
        profilePhoneNumberEditText.isEnabled = true

        editProfileButton.visibility = View.GONE
        logoutButton.visibility = View.GONE
    }

    private fun confirmUpdateUser() {
        user.firstName = profileFirstNameEditText.text.toString()
        user.lastName = profileLastNameEditText.text.toString()
        user.citizenId = profileCitizenIdEditText.text.toString()
        user.phoneNumber = profilePhoneNumberEditText.text.toString()

        UserProfileService.editUserProfile(user)

        val resultIntent = Intent()
        resultIntent.putExtra(USER_PARCEL_KEY, user)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun logout() {
        val sharedPref = getSharedPreferences(SplashScreenActivity.SHARE_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.clear()
        editor.apply()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
