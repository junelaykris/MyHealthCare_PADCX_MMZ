package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.viewholders.MedicineListViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import mk.monthlytut.patient.delegates.CheckoutDelegate

class CheckoutAdpater(private val mDelegate: CheckoutDelegate) :
        BaseRecyclerAdapter<MedicineListViewHolder, PrescriptionVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineListViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_medicine, parent, false)
        return MedicineListViewHolder(view, mDelegate)

    }
}
