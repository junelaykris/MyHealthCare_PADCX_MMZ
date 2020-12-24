package com.padcx.mmz.myhealthcare.utils

import com.padcx.mmz.shared.data.vos.PatientVO

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class MyUserManager {
    companion object {
        private val prefManager = PreferenceManager.INSTANCE!!

        private var user: PatientVO? = null
        private val KEY = "userData"

        fun getUser(): PatientVO {
            if (user == null) {
                user = prefManager.getObject(KEY) ?: PatientVO()
            }
            return user!!
        }

        fun saveUser(user: PatientVO) {
            prefManager.putObject(KEY, user)
            this.user = user
        }

        fun clearUser() {
            this.user = null
            prefManager.putObject(KEY, null)
        }
    }

}