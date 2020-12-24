package com.padcx.mmz.shared.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
abstract class BaseFragment : Fragment(), BaseView
{
    override fun showError(error: String) {
        //   Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }
    inline fun <reified T : AbstractBasePresenter<W>, reified W: BaseView> getPresenter(): T {
        val presenter = ViewModelProviders.of(this).get(T::class.java)
        if (this is W) presenter.initPresenter(this)
        return presenter
    }
}