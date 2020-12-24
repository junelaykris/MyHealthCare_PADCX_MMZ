package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.viewholders.ShippingAddressViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import mk.monthlytut.patient.delegates.ShippingAddressDelegate

class ShippingAddressAdapter( private val mDelegate: ShippingAddressDelegate, var mpreviousPosition: Int) :
        BaseRecyclerAdapter<ShippingAddressViewHolder, String>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShippingAddressViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_shipping_address, parent, false)
        return ShippingAddressViewHolder(view,  mpreviousPosition, mDelegate)

    }
}
