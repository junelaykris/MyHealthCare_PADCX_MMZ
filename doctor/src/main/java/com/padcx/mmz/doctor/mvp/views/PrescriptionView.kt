package com.padcx.mmz.doctor.mvp.views

import com.padcx.mmz.shared.data.vos.MedicineVO
import com.padcx.mmz.shared.views.BaseView

interface PrescriptionView : BaseView {
    fun displayMedicineList(list: List<MedicineVO>)
    fun displayRoutineChooseDialog(medicineVO: MedicineVO)
    fun removeMedicine(medicineVO: MedicineVO)
    fun finishConsultation()
}