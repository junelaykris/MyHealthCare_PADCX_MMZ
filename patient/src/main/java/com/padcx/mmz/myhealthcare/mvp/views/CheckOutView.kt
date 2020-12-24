package com.padcx.mmz.myhealthcare.mvp.views

import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.views.BaseView

interface CheckOutView : BaseView {
    fun displayPrescription(list: List<PrescriptionVO>)
    fun displayShippingAddress (list : List<String>)
    fun displayConfirmDialog(list: List<PrescriptionVO>, total_price: String, address: String)
    fun selectedShippingAddress(address: String, previousPostion: Int)
}