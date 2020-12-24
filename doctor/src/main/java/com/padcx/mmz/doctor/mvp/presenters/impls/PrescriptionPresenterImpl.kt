package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.doctor.mvp.presenters.PrescriptionPresenter
import com.padcx.mmz.doctor.mvp.views.PrescriptionView
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.MedicineVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter


class PrescriptionPresenterImpl : PrescriptionPresenter, AbstractBasePresenter<PrescriptionView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpl
    lateinit var mOwner: LifecycleOwner

    override fun onTapFinishConsultation(
        list: List<PrescriptionVO>,
        consultationChatVO: ConsultationChatVO
    ) {
        mDoctorModel.finishConsultation(consultationChatVO, list, onSuccess = {}, onError = {})
        mView?.finishConsultation()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mOwner = owner
    }

    override fun onTapSelectMedicine(medicineVO: MedicineVO) {
        mView?.displayRoutineChooseDialog(medicineVO)
    }

    override fun onTapRemoveMedicine(medicineVO: MedicineVO) {
        mView?.removeMedicine(medicineVO)
    }

    override fun onUiReadyForPrescription(speciality: String) {
        mDoctorModel.getAllMedicine(speciality, onSuccess = {}, onError = {})

        mDoctorModel.getAllMedicineFromDB()
            .observe(mOwner, Observer {
                it?.let {
                    mView?.displayMedicineList(it)
                }
            })
    }
}

