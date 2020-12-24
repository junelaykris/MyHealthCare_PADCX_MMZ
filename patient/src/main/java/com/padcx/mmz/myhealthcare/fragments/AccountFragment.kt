package com.padcx.mmz.myhealthcare.fragments

import android.content.Context
import com.padcx.mmz.shared.fragment.BaseFragment

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class AccountFragment: BaseFragment() {

    companion object {
        lateinit var mContext: Context
        fun newInstance(context: Context): AccountFragment {
            mContext = context
            return AccountFragment()
        }
    }
}