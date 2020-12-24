package com.padcx.mmz.myhealthcare.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.myhealthcare.mvp.views.CheckOutView
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.presenter.BasePresenter
import mk.monthlytut.patient.delegates.CheckoutDelegate
import mk.monthlytut.patient.delegates.ShippingAddressDelegate

interface CheckoutPresenter : BasePresenter<CheckOutView>, CheckoutDelegate ,ShippingAddressDelegate{
    fun onUiReadyCheckout( consultationChatId : String ,  owner: LifecycleOwner)
    fun onTapCheckout(prescriptionList : List<PrescriptionVO>, deliveryAddressVO: String,
                      doctorVO: DoctorVO?, patientVO: PatientVO?, total_price : String)
    fun onTapAddShipping(patientVO: PatientVO?)
}