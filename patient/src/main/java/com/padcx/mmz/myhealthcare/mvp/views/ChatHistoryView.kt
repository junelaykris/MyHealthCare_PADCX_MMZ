package com.padcx.mmz.myhealthcare.mvp.views

import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.views.BaseView

interface ChatHistoryView : BaseView {
   fun displayChatHistoryList( list: List<ConsultationChatVO>)
   fun nextPageToChatRoom(ChatId : String)
   fun showPrescriptionDialog(finishConsultation:Boolean ,chatID : String,
                              pname: String,startDate : String)
}