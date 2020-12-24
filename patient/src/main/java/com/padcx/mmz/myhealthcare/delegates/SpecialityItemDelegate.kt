package com.padcx.mmz.myhealthcare.delegates

import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.SpecialitiesVO

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
interface SpecialityItemDelegate {
    fun onTapSpecialityItem(specialitiesVO: SpecialitiesVO)
}