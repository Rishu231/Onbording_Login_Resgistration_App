package com.example.softwarelab

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.softwarelab.Login.LoginActivity
import com.example.softwarelab.Signup.SignupActivity
import com.example.softwarelab.sharedPreference.PrefsUtil

class OnboardingActivity : AppCompatActivity(), OnboardingFragment1.OnboardingFragmentCallback,OnboardingFragment2.OnboardingFragmentCallback, OnboardingFragment3.OnboardingFragmentCallback {
    private lateinit var prefsUtil: PrefsUtil
    lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnboardingPagerAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private val delayMillis: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        prefsUtil = PrefsUtil(this)
        prefsUtil.onboarding_complete = false
        viewPager = findViewById(R.id.viewPager)
        adapter = OnboardingPagerAdapter(this)
        viewPager.adapter = adapter

        startAutoSlide()
    }

    private val autoSlideRunnable = object : Runnable {
        override fun run() {
            if (currentPage < adapter.itemCount) {
                viewPager.currentItem = currentPage++
            } else {
                currentPage = 0
                viewPager.currentItem = currentPage
            }
            handler.postDelayed(this, delayMillis)
        }
    }

    private fun startAutoSlide() {
        handler.post(autoSlideRunnable)
    }

    private fun stopAutoSlide() {
        handler.removeCallbacks(autoSlideRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoSlide()
    }

    override fun onNextPageRequested() {
        prefsUtil.onboarding_complete = true
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginClick() {
        prefsUtil.onboarding_complete = true
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}