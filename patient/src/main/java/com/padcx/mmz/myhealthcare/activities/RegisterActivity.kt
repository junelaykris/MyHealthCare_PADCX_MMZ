package com.padcx.mmz.myhealthcare.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.installations.FirebaseInstallations
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.mvp.presenters.RegisterPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.RegisterPresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.RegisterView
import com.padcx.mmz.shared.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class RegisterActivity:BaseActivity(),RegisterView {

    private lateinit var mPresenter: RegisterPresenter
    private lateinit var token : String

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

       /* FirebaseInstallations.getInstance().getToken(false).addOnCompleteListener {
            token =it.result.token
            Log.d("fbToken", it.result.token)
        }*/
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<RegisterPresenterImpl, RegisterView>()
        mPresenter.onUiReady(this,this)
    }

    private fun setUpActionListeners() {

        txt_back.setOnClickListener{
            onBackPressed()
        }

        btnRegister.setOnClickListener {
            when{
                edtEmail.text.isNullOrBlank()-> edtEmail.error = "Email is Required"
                edtUserName.text.isNullOrBlank() -> edtUserName.error = "UserName required"
                edtPassword.text.isNullOrBlank() -> edtPassword.error = "Password required"
                else->{

                    mPresenter.onTapRegister(this,
                        edtUserName.text.toString(),
                        edtEmail.text.toString(),
                        edtPassword.text.toString(),
                        token
                    )
                }
            }

        }
    }

    override fun navigateToToLoginScreen() {
        startActivity(LoginActivity.newIntent(this))
        this.finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this,""+error, Toast.LENGTH_SHORT).show()
    }
}