package com.padcx.mmz.myhealthcare.viewholders

import android.view.View
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.listitem_medicine.view.*
import mk.monthlytut.patient.delegates.CheckoutDelegate


class MedicineListViewHolder(itemView: View, private val mDelegate: CheckoutDelegate) :
        BaseViewHolder<PrescriptionVO>(itemView) {

    override fun bindData(data: PrescriptionVO) {

        data?.let {
            itemView.txt_price.text = data.price +" ကျပ်"
            itemView.txt_tablet.text = data.count +" ကတ်"
            itemView.txt_medicinename.text = data.medicine_name
        }

    }
}