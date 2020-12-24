package com.padcx.mmz.shared.fragment

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import com.padcx.mmz.shared.views.BaseView


abstract class BaseDialogFragment : DialogFragment(), BaseView
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