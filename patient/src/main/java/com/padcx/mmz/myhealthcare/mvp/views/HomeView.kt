package com.padcx.mmz.myhealthcare.mvp.views

import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.RecentDoctorVO
import com.padcx.mmz.shared.data.vos.SpecialitiesVO
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface HomeView : BaseView {
    fun showRecentDoctorList (recentDoctorList : List<RecentDoctorVO>)
    fun showSpecialityList(list: List<SpecialitiesVO>)
    fun showCaseSummaryPageDialog(specialitiesVO: SpecialitiesVO)
    fun nextPageToCaseSummaryFromRecentDoctor( doctorVO: RecentDoctorVO)
    fun displayConsultationRequest(consultationRequestVO: List<ConsultationRequestVO>)
    fun startChattingRoom(consultationID : String,consultationRequestVO: ConsultationRequestVO)
}