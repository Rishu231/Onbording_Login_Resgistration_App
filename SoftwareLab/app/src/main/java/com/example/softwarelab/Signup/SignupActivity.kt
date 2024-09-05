package com.example.softwarelab.Signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.softwarelab.Login.LoginActivity
import com.example.softwarelab.MainActivity
import com.example.softwarelab.Models.BusinessHours
import com.example.softwarelab.Models.Register
import com.example.softwarelab.Models.RegistrationResponse
import com.example.softwarelab.R
import com.example.softwarelab.Signup.view.ISignupView
import com.example.softwarelab.sharedPreference.PrefsUtil
import java.util.UUID

class SignupActivity : AppCompatActivity(), ISignupView {
    private lateinit var prefsUtil: PrefsUtil
    private lateinit var presenter: SignupPresenter
    private lateinit var Signup_main: ConstraintLayout
    private lateinit var farm_main: ConstraintLayout
    private lateinit var verification_main: ConstraintLayout
    private lateinit var file_name_txt: TextView
    private lateinit var files_main: LinearLayout
    private lateinit var Business_main: ConstraintLayout
    private val PICK_PDF_REQUEST = 1
    private var selectedPdfFilename: String = ""
    private var selectedState: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        presenter = SignupPresenter(this)
        prefsUtil = PrefsUtil(this)
        val token: String = UUID.randomUUID().toString()
        val social_token: String = UUID.randomUUID().toString()

        //welcome ui
        Signup_main = findViewById<ConstraintLayout>(R.id.Signup_main)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etPasswordConfirm = findViewById<EditText>(R.id.etPasswordConfirm)
        val buttonLogin1 = findViewById<TextView>(R.id.buttonLogin1)
        val btnContinue1 = findViewById<Button>(R.id.btnContinue1)

        //farm info Ui
        farm_main = findViewById<ConstraintLayout>(R.id.farm_main)
        val etBname = findViewById<EditText>(R.id.etBname)
        val etInformalName = findViewById<EditText>(R.id.etInformalName)
        val etStreet = findViewById<EditText>(R.id.etStreet)
        val etCity = findViewById<EditText>(R.id.etCity)
        val etState = findViewById<Spinner>(R.id.etState)
        val etZipcode = findViewById<EditText>(R.id.etZipcode)
        val buttonLogin1farm = findViewById<TextView>(R.id.buttonLogin1farm)
        val btnContinue1farm = findViewById<Button>(R.id.btnContinue1farm)

        //verification Ui
        verification_main = findViewById<ConstraintLayout>(R.id.verification_main)
        val rel_com_profile_layout = findViewById<RelativeLayout>(R.id.rel_com_profile_layout)
        files_main = findViewById<LinearLayout>(R.id.files_main)
        file_name_txt = findViewById<TextView>(R.id.file_name_txt)
        val buttonBackVerification = findViewById<TextView>(R.id.buttonBackVerification)
        val btnSubmitVerification = findViewById<Button>(R.id.btnSubmitVerification)

        //Business hours Ui
        Business_main = findViewById<ConstraintLayout>(R.id.Business_main)
        val tv_monday = findViewById<TextView>(R.id.tv_monday)
        val tv_tuesday = findViewById<TextView>(R.id.tv_tuesday)
        val tv_wednesday = findViewById<TextView>(R.id.tv_wednesday)
        val tv_thursday = findViewById<TextView>(R.id.tv_thursday)
        val tv_friday = findViewById<TextView>(R.id.tv_friday)
        val tv_saturday = findViewById<TextView>(R.id.tv_saturday)
        val tv_sunday = findViewById<TextView>(R.id.tv_sunday)
        val timing_grid = findViewById<GridLayout>(R.id.timing_grid)
        val first_timing = findViewById<TextView>(R.id.first_timing)
        val second_timing = findViewById<TextView>(R.id.second_timing)
        val third_timing = findViewById<TextView>(R.id.third_timing)
        val forth_timing = findViewById<TextView>(R.id.forth_timing)
        val fifth_timing = findViewById<TextView>(R.id.fifth_timing)
        val buttonBackBusiness = findViewById<TextView>(R.id.buttonBackBusiness)
        val btnSubmitBusiness = findViewById<Button>(R.id.btnSubmitBusiness)


        //welcome
        btnContinue1.setOnClickListener {
            if (!etName.text.isNullOrEmpty() && !etEmail.text.isNullOrEmpty() && !etPhone.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty() && !etPasswordConfirm.text.isNullOrEmpty()) {
                if (etPassword.text.toString() == etPasswordConfirm.text.toString()) {
                    Signup_main.visibility = View.GONE
                    farm_main.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Both Password Not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonLogin1.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //farm info

        val statesArray = resources.getStringArray(R.array.indian_states)
        val items = listOf("Select State") + statesArray.toList()

        val adapter = ArrayAdapter(this, R.layout.custom_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        etState.adapter = adapter
        etState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedState = parent.getItemAtPosition(position).toString()
                if (position != 0) {
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where nothing is selected
            }
        }

        buttonLogin1farm.setOnClickListener {
            farm_main.visibility = View.GONE
            Signup_main.visibility = View.VISIBLE
        }

        btnContinue1farm.setOnClickListener {
            if(!etBname.text.isNullOrEmpty() && !etInformalName.text.isNullOrEmpty() && !etStreet.text.isNullOrEmpty() && !etCity.text.isNullOrEmpty() && !selectedState.isNullOrEmpty() && !etZipcode.text.isNullOrEmpty()){
                farm_main.visibility = View.GONE
                verification_main.visibility = View.VISIBLE
            }else{
                Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show()
            }
        }

        //verification
        rel_com_profile_layout.setOnClickListener {
            openPdfPicker()
        }
        file_name_txt.setOnClickListener {
            files_main.visibility = View.GONE
        }

        buttonBackVerification.setOnClickListener {
            verification_main.visibility = View.GONE
            farm_main.visibility = View.VISIBLE
        }

        btnSubmitVerification.setOnClickListener {
            verification_main.visibility = View.GONE
            Business_main.visibility = View.VISIBLE
        }

        //Business
        buttonBackBusiness.setOnClickListener {
            Business_main.visibility = View.GONE
            verification_main.visibility = View.VISIBLE
        }

        tv_monday.tag = "inactive"
        tv_tuesday.tag = "inactive"
        tv_wednesday.tag = "active"
        tv_thursday.tag = "inactive"
        tv_friday.tag = "inactive"
        tv_saturday.tag = "inactive"
        tv_sunday.tag = "inactive"

        tv_monday.setOnClickListener { toggleSelectionDay(it as TextView) }
        tv_tuesday.setOnClickListener { toggleSelectionDay(it as TextView) }
        tv_wednesday.setOnClickListener { toggleSelectionDay(it as TextView) }
        tv_thursday.setOnClickListener { toggleSelectionDay(it as TextView) }
        tv_friday.setOnClickListener { toggleSelectionDay(it as TextView) }
        tv_saturday.setOnClickListener { toggleSelectionDay(it as TextView) }
        tv_sunday.setOnClickListener { toggleSelectionDay(it as TextView) }


        first_timing.tag = "active"
        second_timing.tag = "inactive"
        third_timing.tag = "inactive"
        forth_timing.tag = "inactive"
        fifth_timing.tag = "inactive"
        first_timing.setOnClickListener { toggleSelectionDay(it as TextView) }
        second_timing.setOnClickListener { toggleSelection(it as TextView) }
        third_timing.setOnClickListener { toggleSelection(it as TextView) }
        forth_timing.setOnClickListener { toggleSelection(it as TextView) }
        fifth_timing.setOnClickListener { toggleSelection(it as TextView) }


        btnSubmitBusiness.setOnClickListener {
            val businessHours = BusinessHours(
                mon = listOf(first_timing.text.toString()),
                tue = listOf(second_timing.text.toString()),
                wed = listOf(third_timing.text.toString()),
                thu = listOf(forth_timing.text.toString()),
                fri = listOf(fifth_timing.text.toString()),
                sat = listOf(fifth_timing.text.toString()),
                sun = listOf("Closed")
            )

            val register = Register(
                full_name = etName.text.toString(),
                email = etEmail.text.toString(),
                phone = "+91"+etPhone.text.toString(),
                password = etPassword.text.toString(),
                role = "farmer",
                businessName = etBname.text.toString(),
                informalName = etInformalName.text.toString(),
                address = etStreet.text.toString(),
                city = etCity.text.toString(),
                state = selectedState,
                zipCode = etZipcode.text.toString().toInt(),
                registration_proof = selectedPdfFilename,
                business_hours = businessHours,
                device_token = token,
                type = "Regular",
                social_id = social_token
            )
            prefsUtil.social_token = social_token
            if (true) {
                presenter.Registeration(register)
            }else{
                Toast.makeText(this, "Please select one or two day and time", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleSelectionDay(textView: TextView) {
        val isCurrentlyActive = textView.tag == "active"
        if (isCurrentlyActive) {
            // Unselect the TextView
            textView.setBackgroundResource(R.drawable.day_button_background)
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            textView.tag = "inactive"
        } else {
            // Select the TextView
            textView.setBackgroundResource(R.drawable.day_button_active_background)
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
            textView.tag = "active"
        }
    }


    private fun toggleSelection(textView: TextView) {
        val isCurrentlyActive = textView.tag == "active"
        if (isCurrentlyActive) {
            // Unselect the TextView
            textView.setBackgroundResource(R.drawable.time_slot_inactive_background)
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            textView.tag = "inactive"
        } else {
            // Select the TextView
            textView.setBackgroundResource(R.drawable.time_slot_active_background)
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            textView.tag = "active"
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun saveToken(data: RegistrationResponse) {
        if (data.token != null && data.message=="Registered." ) {
            prefsUtil.token = data.token.toString()
            prefsUtil.signup_complete = true
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Business_main.visibility = View.GONE
            Signup_main.visibility = View.VISIBLE
            Toast.makeText(this, "You Account is Created", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun openPdfPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        startActivityForResult(intent, PICK_PDF_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                var fileName: String? = null
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (nameIndex != -1) {
                            fileName = it.getString(nameIndex)
                        }
                    }
                }
                files_main.visibility = View.VISIBLE
                file_name_txt.text = fileName.toString()
                selectedPdfFilename = fileName.toString()
            }
        }
    }




}