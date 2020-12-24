package com.padcx.mmz.myhealthcare.viewholders

import android.util.Log
import android.view.View
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.listitem_shipping_address.view.*
import mk.monthlytut.patient.delegates.ShippingAddressDelegate

class ShippingAddressViewHolder(itemView: View, var previousPosition : Int,private val mDelegate: ShippingAddressDelegate) :
        BaseViewHolder<String>(itemView) {

    override fun bindData(data: String) {
        itemView.listitem_address.setBackgroundResource(R.drawable.bg_rounded_border_grey)
        Log.d("previous Postion ${previousPosition}","currentPosition ${adapterPosition}")
        if(adapterPosition == previousPosition)
        {
            itemView.listitem_address.setBackgroundResource(R.drawable.rounded_corner_blue)
        }
        data?.let {
            itemView.listitem_address.text =data
        }

        itemView.listitem_address.setOnClickListener {
            mDelegate.onTapSelected(data,adapterPosition)
        }

    }
}