package com.padcx.mmz.myhealthcare.viewholders

import android.view.View
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.delegates.SpecialityItemDelegate
import com.padcx.mmz.myhealthcare.utils.showCropImage
import com.padcx.mmz.shared.data.vos.SpecialitiesVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.speciality_list_item.view.*

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
class SpecialityViewHolder(itemView: View, private val mDelegate: SpecialityItemDelegate) :
    BaseViewHolder<SpecialitiesVO>(itemView) {

    override fun bindData(data: SpecialitiesVO) {

        itemView.speciality_name.text = data.name

            itemView.iv_speciality.showCropImage(data.photo)


        itemView.cl_speciality.setOnClickListener{
            mDelegate.onTapSpecialityItem(data)
        }
    }
}