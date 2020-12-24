package com.padcx.mmz.myhealthcare.delegates

import com.padcx.mmz.shared.data.vos.RecentDoctorVO

interface RecentDoctorViewItemActionDelegate {
    fun onTapRecentDoctor(doctorVO: RecentDoctorVO)
}