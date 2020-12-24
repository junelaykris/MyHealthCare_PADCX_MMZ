package com.padcx.mmz.doctor.delegates

import com.padcx.mmz.shared.data.vos.MedicineVO


interface MedicineDelegate {
    fun onTapSelectMedicine(medicineVO: MedicineVO)
    fun onTapRemoveMedicine(medicineVO: MedicineVO)
}