package com.padcx.mmz.doctor.mvp.presenters

import com.padcx.mmz.doctor.delegates.ConsultationHistoryItemDelegate
import com.padcx.mmz.doctor.delegates.ConsultationRequestDelegate
import com.padcx.mmz.doctor.mvp.views.LoginView
import com.padcx.mmz.doctor.views.HomeView
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
interface HomePresenter : BasePresenter<HomeView>, ConsultationRequestDelegate, ConsultationHistoryItemDelegate{
}