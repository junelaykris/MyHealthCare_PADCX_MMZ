package com.padcx.mmz.myhealthcare.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.myhealthcare.delegates.ChatRoomDelegate
import com.padcx.mmz.myhealthcare.delegates.SpecialQuestionDelegate
import com.padcx.mmz.myhealthcare.mvp.views.ChatView
import com.padcx.mmz.myhealthcare.viewpods.PrescriptionViewPod
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 12/17/2020.
 */
interface ChatPresenter : BasePresenter<ChatView>, ChatRoomDelegate, PrescriptionViewPod.Delegate{
    fun onUiReadyChatting(consultationChatId: String, owner: LifecycleOwner)

    fun addTextMessage(
        message: String, consultationChatId: String, senderId: String,
        senderPhoto: String, senderName: String, owner: LifecycleOwner
    )
}