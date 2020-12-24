package com.padcx.mmz.myhealthcare.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

import com.padcx.mmz.myhealthcare.mvp.presenters.ChatPresenter
import com.padcx.mmz.myhealthcare.mvp.views.ChatView
import com.padcx.mmz.myhealthcare.utils.getCurrentDateTime
import com.padcx.mmz.myhealthcare.utils.getCurrentHourMin
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.data.vos.ChatMessageVO
import com.padcx.mmz.shared.data.vos.SenderVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import java.util.*

/**
 * Created by Myint Myint Zaw on 12/17/2020.
 */
class ChatPresenterImpl : ChatPresenter, AbstractBasePresenter<ChatView>() {

    private val mPatientModel: PatientModel = PatientModelImpl
    lateinit var mLifecycleOwner: LifecycleOwner

    override fun onUiReadyChatting(consultationChatId: String, owner: LifecycleOwner) {

        mLifecycleOwner = owner

        mPatientModel.getPrescription(consultationChatId, onSuccess = {}, onError = {})

        mPatientModel.getPrescriptionFromDB()
            .observe(mLifecycleOwner, Observer {
                it?.let {
                    mView?.displayPrescriptionViewPod(it)
                }
            })

        mPatientModel.getConsultationChat(consultationChatId, onSuccess = {}, onError = {})

        mPatientModel.getConsultationChatFromDB(consultationChatId)
            .observe(owner, Observer { data ->
                data?.let {
                    mView?.displayPatientInfo(data)
                }
            })

        mPatientModel.getChatMessage(consultationChatId, onSuccess = {}, onError = {})

        mPatientModel.getAllChatMessageFromDB().observe(owner, Observer { data ->
                data?.let {
                    mView?.displayChatMessageList(data)
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
        mPatientModel.sendChatMessage(chatMessage, consultationChatId, onSuccess = {}, onError = {})
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

    override fun onTapPrescription(chatId: String) {
        mView?.nextPageToCheckout(chatId)
    }

    override fun onTapDoctorComment() {

    }
}