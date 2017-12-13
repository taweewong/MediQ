package com.nonggun.mediq.controllers.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity
import com.nonggun.mediq.controllers.login.LoginActivity
import com.nonggun.mediq.controllers.queue.InQueueActivity
import com.nonggun.mediq.controllers.queue.QueueActivity
import com.nonggun.mediq.models.User
import com.nonggun.mediq.services.ClientInQueueService
import com.nonggun.mediq.services.UserProfileService

class SplashScreenActivity : BaseActivity(), UserProfileService.OnGetUserComplete,
        ClientInQueueService.OnGetUserQueueListener {

    companion object {
        val SHARE_PREFS_NAME = "loginPref"
        val SHARE_USER_KEY = "userKey"
        private val NOT_FOUND = "not found"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        checkExistUser()
    }

    private fun checkExistUser() {
        val sharedPref = getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE)
        val userId = sharedPref.getString(SHARE_USER_KEY, NOT_FOUND)

        if (userId == NOT_FOUND) {
            openLoginActivity()
        } else {
            UserProfileService.getUser(userId, this)
        }
    }

    override fun onGetUserSuccess(user: User) {
        checkUserQueue(user)
    }

    override fun onGetUserFailed(message: String) {
        openLoginActivity()
    }

    private fun checkUserQueue(user: User) {
        ClientInQueueService.getUserQueue(user, this)
    }

    override fun onGetCurrentQueueSuccess(user: User, userQueue: Int) {
        sendUserDataToQueueActivity(user, InQueueActivity::class.java)
        Toast.makeText(this, "Queue Found", Toast.LENGTH_SHORT).show()
    }

    override fun onGetCurrentQueueFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetCurrentQueueNotFound(user: User) {
        sendUserDataToQueueActivity(user, QueueActivity::class.java)
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun <T>sendUserDataToQueueActivity(user: User, queueClass: Class<T>) {
        intent = Intent(this, queueClass)
        intent.putExtra(User.Key.USER_PARCEL_KEY, user)
        startActivity(intent)
        finish()
    }
}
