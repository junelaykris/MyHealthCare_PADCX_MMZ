package com.padcx.mmz.doctor.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.mvp.presenters.LoginPresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.LoginPresenterImpl
import com.padcx.mmz.doctor.mvp.views.LoginView
import com.padcx.mmz.doctor.utils.MyUserManager
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.DoctorVO
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class LoginActivity:BaseActivity(), LoginView {

    private lateinit var mPresenter: LoginPresenter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpPresenter()
        setUpActionListeners()
    }

    private fun setUpActionListeners() {
        btnLogin.setOnClickListener {
            mPresenter.onTapLogin(this,edt_email.text.toString(), edt_password.text.toString(),this)
        }

        btnRegister.setOnClickListener {
            mPresenter.onTapRegister()
        }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LoginPresenterImpl, LoginView>()
        mPresenter.onUiReady(this,this)
    }

    override fun navigateToHomeScreen(doctorVO: DoctorVO) {
        onAuthenticated(doctorVO)
        startActivity(MainActivity.newIntent(this))
        this.finish()
    }

    private fun onAuthenticated(doctorVO: DoctorVO) {
        MyUserManager.saveUser(doctorVO)
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newIntent(this))
    }

    override fun showError(error: String) {
        Toast.makeText(this,""+error, Toast.LENGTH_SHORT).show()
    }
}