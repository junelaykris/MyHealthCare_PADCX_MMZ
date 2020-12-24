package com.padcx.mmz.doctor.delegates

import com.padcx.mmz.shared.data.vos.ConsultationRequestVO

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
interface ConsultationRequestDelegate {
    fun onTapNext(consultationRequestVO: ConsultationRequestVO)
    fun onTapSkip(consultationRequestVO: ConsultationRequestVO)
    fun onTapPostpone(consultationRequestVO: ConsultationRequestVO)
    fun onTapAccept(consultationRequestVO: ConsultationRequestVO)
    fun onTapPostponeTime(postPoneTime : String, consultationRequestVO: ConsultationRequestVO)
}