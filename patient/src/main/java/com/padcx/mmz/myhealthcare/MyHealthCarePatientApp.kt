package com.padcx.mmz.myhealthcare

import android.app.Application
import com.padcx.mmz.myhealthcare.utils.PreferenceManager
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.persistance.db.MyHealthCareDB

/**
 * Created by Myint Myint Zaw on 12/6/2020.
 */
class MyHealthCarePatientApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencies()
        PatientModelImpl.initDatabase(applicationContext)
    }
    private fun initDependencies() {
        PreferenceManager.newInstance(applicationContext)
    }
}