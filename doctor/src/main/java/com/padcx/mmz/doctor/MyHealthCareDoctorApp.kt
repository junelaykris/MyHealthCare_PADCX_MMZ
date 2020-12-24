package com.padcx.mmz.doctor

import android.app.Application
import com.padcx.mmz.doctor.utils.PreferenceManager
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.persistance.db.MyHealthCareDB

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
class MyHealthCareDoctorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencies()
        DoctorModelImpl.initDatabase(applicationContext)
    }
    private fun initDependencies() {
        PreferenceManager.newInstance(applicationContext)
    }

}