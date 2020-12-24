package com.padcx.mmz.myhealthcare.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.myhealthcare.mvp.presenters.ChatHistoryPresenter
import com.padcx.mmz.myhealthcare.mvp.views.ChatHistoryView
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter


class ChatHistoryPresenterImpl : ChatHistoryPresenter, AbstractBasePresenter<ChatHistoryView>() {

    private val patientModel: PatientModel = PatientModelImpl

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

        patientModel.getConsultationChatByPatientId(
            MyUserManager.getUser().id,
            onSuccess = {},
            onError = {})

        patientModel.getConsultationChatByPatientIdFromDB(MyUserManager.getUser().id)
            .observe(owner, Observer { data ->
                data?.let {
                    mView?.displayChatHistoryList(data)
                }
            })

    }

    override fun onTapSendMessage(consultationChatVO: ConsultationChatVO) {
        mView?.nextPageToChatRoom(consultationChatVO.id)
    }

    override fun onTapPrescription(consultationChatVO: ConsultationChatVO) {
        mView?.showPrescriptionDialog(
            consultationChatVO.finish_consultation_status,
            consultationChatVO.id,
            consultationChatVO.patient?.name.toString(),
            consultationChatVO.start_consultation_date.toString()
        )
    }
}