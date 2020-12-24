package com.padcx.mmz.myhealthcare.mvp.presenters

import android.content.Context
import com.padcx.mmz.myhealthcare.delegates.ConsultationAcceptDelegate
import com.padcx.mmz.myhealthcare.delegates.RecentDoctorViewItemActionDelegate
import com.padcx.mmz.myhealthcare.delegates.SpecialityItemDelegate
import com.padcx.mmz.myhealthcare.mvp.views.HomeView
import com.padcx.mmz.myhealthcare.mvp.views.RegisterView
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.SpecialitiesVO
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface HomePresenter : BasePresenter<HomeView>,SpecialityItemDelegate,
    ConsultationAcceptDelegate, RecentDoctorViewItemActionDelegate {
    fun navigateToNextScreen()
    fun statusUpdateForAcceptChat(context: Context, consultedChatId : String, consultationRequestVO: ConsultationRequestVO)
}