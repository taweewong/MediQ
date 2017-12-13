package com.nonggun.mediq.controllers.profile

import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.WindowManager
import com.nonggun.mediq.R
import com.nonggun.mediq.base.BaseActivity

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ActivityCompat.getColor(this, R.color.bgGray)
        }

        
    }
}
