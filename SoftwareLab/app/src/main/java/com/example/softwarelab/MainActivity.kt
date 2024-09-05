package com.example.softwarelab

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.softwarelab.Login.LoginActivity
import com.example.softwarelab.sharedPreference.PrefsUtil
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity() {
    private lateinit var prefsUtil: PrefsUtil
    private lateinit var complete_login_signup: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefsUtil = PrefsUtil(this)

//        //remove code
//        prefsUtil.login_complete = false

        complete_login_signup = findViewById<ConstraintLayout>(R.id.complete_login_signup)

        if (!prefsUtil.onboarding_complete){
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }else if (!prefsUtil.login_complete){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }else{
            complete_login_signup.visibility = View.VISIBLE
        }

    }
}