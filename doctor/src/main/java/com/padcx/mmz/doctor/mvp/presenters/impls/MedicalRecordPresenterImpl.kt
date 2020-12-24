package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.doctor.mvp.presenters.MedicalRecordPresenter
import com.padcx.mmz.doctor.mvp.views.MedicalRecordView
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter


class MedicalRecordPresenterImpl : MedicalRecordPresenter, AbstractBasePresenter<MedicalRecordView>() {

    private val doctorModel: DoctorModel = DoctorModelImpl

    override fun onTapSaveMedicalRecord(consultationChatVO: ConsultationChatVO, owner: LifecycleOwner) {
        doctorModel.saveMedicalRecord(consultationChatVO,onSuccess = {}, onError = {
            mView?.showSnackBar("ဆေးမှတ်တမ်း မသိမ်းဆည်းနိုင်ပါ")
        })
        mView?.showSnackBar("ဆေးမှတ်တမ်း သိမ်းဆည်းပြီးပါပြီ")
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

}