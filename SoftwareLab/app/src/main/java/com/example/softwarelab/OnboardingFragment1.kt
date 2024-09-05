package com.example.softwarelab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.softwarelab.Login.LoginActivity
import com.example.softwarelab.sharedPreference.PrefsUtil
import org.koin.android.ext.android.inject

class OnboardingFragment1 : Fragment() {
    private var callback: OnboardingFragmentCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnboardingFragmentCallback) {
            callback = context
        } else {
            throw RuntimeException("$context must implement OnboardingFragmentCallback")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding1, container, false)

        // Example: On button click, go to the next page
        val nextButton: Button = view.findViewById(R.id.buttonJoin1)
        val buttonLogin1: TextView = view.findViewById(R.id.buttonLogin1)
        nextButton.setOnClickListener {
            callback?.onNextPageRequested()
//            (activity as? OnboardingActivity)?.onNextPageRequested()
        }

        buttonLogin1.setOnClickListener {
            callback?.onLoginClick()
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    interface OnboardingFragmentCallback {
        fun onNextPageRequested()
        fun onLoginClick()
    }
}
