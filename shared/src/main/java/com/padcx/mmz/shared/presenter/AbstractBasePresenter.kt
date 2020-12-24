package com.padcx.mmz.shared.presenter

import androidx.lifecycle.ViewModel
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */

abstract class AbstractBasePresenter<T: BaseView>:BasePresenter<T>, ViewModel() {
    var mView : T? = null
    override fun initPresenter(view: T) {
        mView = view
    }
}