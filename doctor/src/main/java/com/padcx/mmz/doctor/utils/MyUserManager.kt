package com.padcx.mmz.doctor.utils

import com.padcx.mmz.shared.data.vos.DoctorVO

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class MyUserManager {
    companion object {
        private val prefManager = PreferenceManager.INSTANCE!!

        private var user: DoctorVO? = null
        private val KEY = "userData"

        fun getUser(): DoctorVO {
            if (user == null) {
                user = prefManager.getObject(KEY) ?: DoctorVO()
            }
            return user!!
        }

        fun saveUser(user: DoctorVO) {
            prefManager.putObject(KEY, user)
            this.user = user
        }

        fun clearUser() {
            this.user = null
            prefManager.putObject(KEY, null)
        }
    }

}