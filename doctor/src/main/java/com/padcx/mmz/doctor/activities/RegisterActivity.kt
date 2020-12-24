package com.padcx.mmz.doctor.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.installations.FirebaseInstallations
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.mvp.presenters.RegisterPresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.RegisterPresenterImpl
import com.padcx.mmz.doctor.mvp.views.RegisterView
import com.padcx.mmz.shared.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class RegisterActivity:BaseActivity(), RegisterView {

    private lateinit var mPresenter: RegisterPresenter
    private lateinit var token : String

    private var speciality_type: String? = null
    private var speciality_name: String? = null
    val specialityTypeList = mutableListOf(
        "cardiology",
        "dentist",
        "dermatology",
        "ent",
        "gastroenterology",
        "hepatology",
        "neurology",
        "og",
        "orthopedics",
        "pediartics",
        "radiology",
        "surgery"
    )

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUpPresenter()
        setUpActionListeners()

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("TOKEN", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                token = task.result.token
            })

        /*FirebaseInstallations.getInstance().getToken(false).addOnCompleteListener {
            token =it.result.token
            Log.d("fbToken", it.result.token)
        }*/

      /*  FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Log.d("fbToken", it.token)
            token =it.token
        }*/
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<RegisterPresenterImpl, RegisterView>()
        mPresenter.onUiReady(this,this)
    }

    private fun setUpActionListeners() {

        specialname_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                speciality_name = parent.getItemAtPosition(position).toString()
                speciality_type = specialityTypeList[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        txt_back.setOnClickListener{
            onBackPressed()
        }

        btnRegister.setOnClickListener {
            when{
                etEmail.text.isNullOrBlank()-> etEmail.error = "Email is Required"
                etUserName.text.isNullOrBlank() -> etUserName.error = "UserName required"
                etPassword.text.isNullOrBlank() -> etPassword.error = "Password required"
                else->{

                    mPresenter.onTapRegister(this,
                        etUserName.text.toString(),
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        token,
                        speciality_name.toString(),
                        speciality_type.toString(),
                        ed_phone.text.toString(),
                        ed_degree.text.toString(),
                        ed_biography.text.toString()
                    )
                }
            }

        }
    }

    override fun navigateToLoginScreen() {
        startActivity(LoginActivity.newIntent(this))
        this.finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this,""+error, Toast.LENGTH_SHORT).show()
    }
}