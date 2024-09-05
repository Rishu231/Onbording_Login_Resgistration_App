package com.example.softwarelab.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.softwarelab.Login.view.ILoginView
import com.example.softwarelab.MainActivity
import com.example.softwarelab.Models.Login
import com.example.softwarelab.Models.VerifyOtpResponse
import com.example.softwarelab.R
import com.example.softwarelab.Signup.SignupActivity
import com.example.softwarelab.sharedPreference.PrefsUtil

class LoginActivity : AppCompatActivity(), ILoginView {
    private lateinit var prefsUtil: PrefsUtil
    private lateinit var presenter: LoginPresenter
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private lateinit var login_main: LinearLayout
    private lateinit var forget_main: LinearLayout
    private lateinit var otp_main: LinearLayout
    private lateinit var reset_pass_main: LinearLayout
    private var Otp: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefsUtil = PrefsUtil(this)
        presenter = LoginPresenter(this)

        //login Ui
        login_main = findViewById<LinearLayout>(R.id.login_main)
        val tvCreateAccountLink = findViewById<TextView>(R.id.tvCreateAccountLink)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        //forget Ui
        forget_main = findViewById<LinearLayout>(R.id.forget_main)
        val tvloginforget = findViewById<TextView>(R.id.tvloginforget)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnSendCode = findViewById<Button>(R.id.btnSendCode)

        //Otp Ui
        otp_main = findViewById<LinearLayout>(R.id.otp_main)
        val tvloginotp = findViewById<TextView>(R.id.tvloginotp)
        val otp_edit1 = findViewById<EditText>(R.id.otp_edit1)
        val otp_edit2 = findViewById<EditText>(R.id.otp_edit2)
        val otp_edit3 = findViewById<EditText>(R.id.otp_edit3)
        val otp_edit4 = findViewById<EditText>(R.id.otp_edit4)
        val otp_edit5 = findViewById<EditText>(R.id.otp_edit5)
        val btnotp = findViewById<Button>(R.id.btnotp)
        val buttonLogin1 = findViewById<TextView>(R.id.buttonLogin1)

        //reset ui
        reset_pass_main = findViewById<LinearLayout>(R.id.reset_pass_main)
        val tvloginreset = findViewById<TextView>(R.id.tvloginreset)
        val et_new_password = findViewById<EditText>(R.id.et_new_password)
        val et_confirm_password = findViewById<EditText>(R.id.et_confirm_password)
        val btnSubmitreset = findViewById<Button>(R.id.btnSubmitreset)

        tvCreateAccountLink.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


        btnLogin.setOnClickListener {
            if (!etEmail?.text.isNullOrEmpty() && !etPassword?.text.isNullOrEmpty()){
                val userRequestLogin = Login(
                    email = etEmail?.text.toString(),
                    password = etPassword?.text.toString(),
                    role = "farmer",
                    device_token = prefsUtil.token,
                    type = "Regular",
                    social_id =  prefsUtil.social_token
                )
                presenter.getLogin(userRequestLogin)
            }else{
                Toast.makeText(this, "Please Fill Both Columns", Toast.LENGTH_LONG).show()
            }
        }

        tvForgotPassword.setOnClickListener {
            login_main.visibility = View.GONE
            forget_main.visibility = View.VISIBLE
        }

        btnSendCode.setOnClickListener {
            if (!etPhone.text.isNullOrEmpty()){
                presenter.sendOtp("+91"+etPhone.text.toString())
            }else{
                Toast.makeText(this, "Please Fill Phone Number", Toast.LENGTH_LONG).show()
            }
        }

        btnotp.setOnClickListener {
            if (!otp_edit1.text.isNullOrEmpty() && !otp_edit2.text.isNullOrEmpty() && !otp_edit3.text.isNullOrEmpty() && !otp_edit4.text.isNullOrEmpty() && !otp_edit5.text.isNullOrEmpty()){
                val otp = otp_edit1.text.toString()+otp_edit2.text.toString()+otp_edit3.text.toString()+otp_edit4.text.toString()+otp_edit5.text.toString()
                presenter.submitOtp(otp)
            }else{
                Toast.makeText(this, "Please enter otp", Toast.LENGTH_SHORT).show()
            }
        }

        btnSubmitreset.setOnClickListener {
            val token = Otp
            val password = et_new_password.text.toString()
            val cpassword = et_confirm_password.text.toString()

            if (password.isNotEmpty() && cpassword.isNotEmpty() && token.isNotEmpty()) {
                if (password == cpassword) {
                    presenter.resetPassword(token, password, cpassword)
                } else {
                    showError("Passwords do not match")
                }
            } else {
                showError("Please fill in all fields")
            }
        }
    }

    override fun showError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun showLoginResponse(response: String) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
        prefsUtil.login_complete = true
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showForgotPasswordSuccess(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        forget_main.visibility = View.GONE
        otp_main.visibility = View.VISIBLE
    }

    override fun submitOtpSuccess(OtpData: VerifyOtpResponse) {
        Otp = OtpData.token.toString()
        otp_main.visibility = View.GONE
        reset_pass_main.visibility = View.VISIBLE
        Toast.makeText(this, OtpData.message, Toast.LENGTH_SHORT).show()
    }

    override fun showPasswordResetSuccess(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        login_main.visibility = View.VISIBLE
        reset_pass_main.visibility = View.GONE
    }

}