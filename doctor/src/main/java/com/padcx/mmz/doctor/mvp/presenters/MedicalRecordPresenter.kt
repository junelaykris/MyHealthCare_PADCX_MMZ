package com.padcx.mmz.doctor.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.doctor.mvp.views.MedicalRecordView
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.presenter.BasePresenter

interface MedicalRecordPresenter : BasePresenter<MedicalRecordView>{
  fun onTapSaveMedicalRecord(consultationChatVO: ConsultationChatVO, owner: LifecycleOwner)
}