package com.padcx.mmz.doctor.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.doctor.delegates.ChatRoomDelegate
import com.padcx.mmz.doctor.mvp.views.ChatView
import com.padcx.mmz.doctor.viewpods.PrescriptionViewPod
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 12/16/2020.
 */
interface ChatPresenter : BasePresenter<ChatView>, ChatRoomDelegate, PrescriptionViewPod.Delegate {
    fun onUiReadyChatting(consultationChatId: String, owner: LifecycleOwner)

    fun addTextMessage(
        message: String, consultationChatId: String, senderId: String,
        senderPhoto: String, senderName: String, owner: LifecycleOwner
    )
}