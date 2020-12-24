package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.doctor.mvp.presenters.ChatPresenter
import com.padcx.mmz.doctor.mvp.views.ChatView
import com.padcx.mmz.doctor.utils.getCurrentDateTime
import com.padcx.mmz.doctor.utils.getCurrentHourMin
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.vos.ChatMessageVO
import com.padcx.mmz.shared.data.vos.SenderVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import java.util.*

/**
 * Created by Myint Myint Zaw on 12/16/2020.
 */
class ChatPresenterImpl : ChatPresenter, AbstractBasePresenter<ChatView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpl
    lateinit var mLifecycleOwner: LifecycleOwner

    override fun onUiReadyChatting(consultationChatId: String, owner: LifecycleOwner) {

        mLifecycleOwner = owner

        mDoctorModel.getConsultationChat(consultationChatId, onSuccess = {}, onError = {})

        mDoctorModel.getConsultationChatFromDB(consultationChatId)
            .observe(owner, Observer { data ->
                data?.let {
                    mView?.displayPatientInfo(data)
                }
            })


        mDoctorModel.getChatMessage(consultationChatId, onSuccess = {}, onError = {})

        mDoctorModel.getAllChatMessageFromDB()
            .observe(owner, Observer { data ->
                data?.let {
                    mView?.displayChatMessageList(data)
                }
            })

        mDoctorModel.getPrescription(consultationChatId, onSuccess = {}, onError = {})

        mDoctorModel.getPrescriptionFromDB(consultationChatId)
            .observe(owner, Observer {
                it?.let {
                    mView?.displayPrescriptionViewPod(it)
                }
            })

    }

    override fun addTextMessage(
        message: String,
        consultationChatId: String,
        type: String,
        senderPhoto: String,
        senderName: String,
        owner: LifecycleOwner
    ) {
        val id = UUID.randomUUID().toString()
        var sendBy = SenderVO(
            photo = senderPhoto,
            name = senderName,
            arrived_time = getCurrentHourMin()
        )
        var chatMessage = ChatMessageVO(
            id = id, message, "", getCurrentDateTime(), sendBy, type
        )
        mDoctorModel.sendChatMessage(chatMessage, consultationChatId, onSuccess = {}, onError = {})
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

    override fun onTapSendTextMessage(message: String) {

    }

    override fun onTapAttachImage() {

    }

    override fun onTapQuestionTemplate() {

    }

    override fun onTapPrescription() {

    }

    override fun onTapDoctorComment() {

    }
}