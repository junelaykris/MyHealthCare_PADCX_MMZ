package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.doctor.mvp.presenters.GeneralQuestionTemplatePresenter
import com.padcx.mmz.doctor.mvp.views.GeneralQuestionTemplateView
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.vos.GeneralQuestionTemplateVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter

class GeneralQuestionTemplatePresenterImpl : GeneralQuestionTemplatePresenter, AbstractBasePresenter<GeneralQuestionTemplateView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpl

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mDoctorModel.getGeneralQuestionTemplate(onSuccess = {}, onError = {})
        mDoctorModel.getGeneralQuestionTemplateFromDB()
                .observe(owner, Observer {
                    mView?.displayGeneralQuestions(it)
                })
    }

    override fun onTapOneQuestion(generalQuestionTemplateVO: GeneralQuestionTemplateVO) {
        mView?.navigateToToChatRoom(generalQuestionTemplateVO.question.toString())
    }
}