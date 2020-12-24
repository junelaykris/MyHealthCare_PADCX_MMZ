package com.padcx.mmz.myhealthcare.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.myhealthcare.mvp.presenters.HomePresenter
import com.padcx.mmz.myhealthcare.mvp.views.HomeView
import com.padcx.mmz.shared.data.models.AuthenticationModel
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.AuthenticationModelImpl
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.SpecialitiesVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import androidx.lifecycle.Observer
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.RecentDoctorVO

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private val mPatientModel: PatientModelImpl = PatientModelImpl

    override fun navigateToNextScreen() {

    }

    override fun statusUpdateForAcceptChat(
        context: Context,
        consultedChatId: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        mPatientModel.joinedChatRoomPatient(consultedChatId,consultationRequestVO,
            onSuccess = {}, onError = {})
    }


    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mPatientModel.getSpecialitiesList(onSuccess = {}, onError = {})

        mPatientModel.getSpecialitiesListFromDB()
            .observe(owner, Observer {
                mView?.showSpecialityList(it)
            })

        mPatientModel.getConsultationAccepts(MyUserManager.getUser().id, onSuccess = {}, onError = {})

        mPatientModel.getConsultationAcceptsFromDB()
            .observe(owner, Observer {
                var data =it.filter{
                    it.consultation_id.toString().isNotEmpty()
                }
                mView?.displayConsultationRequest(data)
            })


        mPatientModel.getRecentDoctors(MyUserManager.getUser().id , onSuccess = {}, onError = {})

        mPatientModel.getRecentDoctorsFromDB()
            .observe(owner, Observer {
                mView?.showRecentDoctorList(it)
            })


        /* mPatientModel.getRecentlyConsultedDoctorFromApi("")
         mPatientModel.getRecentlyConsultedDoctorFromDb().observe(owner,
             Observer {
             mView?.showRecentDoctorList(it)
         })*/
    }

    override fun onTapSpecialityItem(specialitiesVO: SpecialitiesVO) {
        mView?.showCaseSummaryPageDialog(specialitiesVO)
    }

    override fun onTapStarted(
        consultationChatId: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        mView?.startChattingRoom(consultationChatId , consultationRequestVO)
    }

    override fun onTapRecentDoctor(doctorVO: RecentDoctorVO) {
        mView?.nextPageToCaseSummaryFromRecentDoctor(doctorVO)
    }


}