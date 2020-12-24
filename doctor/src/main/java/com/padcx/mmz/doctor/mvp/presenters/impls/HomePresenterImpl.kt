package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.doctor.mvp.presenters.HomePresenter
import com.padcx.mmz.doctor.utils.MyUserManager
import com.padcx.mmz.doctor.views.HomeView
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import com.padcx.mmz.shared.utils.prepareNotification
import com.padcx.mmz.shared.utils.prepareNotificationForDoctor

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpl
    lateinit var mLifecycleOwner: LifecycleOwner
    lateinit var mDoctorVO: DoctorVO
    lateinit var mContext : Context

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mLifecycleOwner = owner
        mContext= context

        mDoctorModel.getBroadCastConsultationRequest(
            MyUserManager.getUser().speciality.toString(),
            onSuccess = {},
            onError = {})

        mDoctorModel.getBroadcastConsultationRequestsFromDB(MyUserManager.getUser().speciality.toString())
            .observe(owner, Observer { consultationRequest ->
                consultationRequest?.let {
                    val data = consultationRequest.filter {
                        it.status.toString() == "none"
                    }
                    val filterdata : ArrayList<ConsultationRequestVO> = arrayListOf()
                    for(item in data)
                    {
                        if(item.doctor_info.id.isNotEmpty())
                        {
                            if(item.doctor_info.id == MyUserManager.getUser().id)
                            {
                                filterdata.add(item)
                            }
                        }else{
                            filterdata.add(item)
                        }
                    }
                    mView?.displayConsultationRequests(filterdata)
                }
            })

        mDoctorModel.getDoctorByEmailFromDB(MyUserManager.getUser().email.toString())
            .observe(owner, Observer { doctor ->
                doctor?.let {
                    mDoctorVO = doctor
                }
            })


        mDoctorModel.getConsultationByDoctorId(
            MyUserManager.getUser().id,
            onSuccess = {},
            onError = {})

        mDoctorModel.getConsultationByDoctorIdFromDB(MyUserManager.getUser().id)
            .observe(owner, Observer { data ->
                data?.let {
                    mView?.displayConsultationList(data)
                }
            })


        mDoctorModel.getConsultedPatient(MyUserManager.getUser().id, onSuccess = {}, onError = {})

        mDoctorModel.getConsultedPatientFromDB(MyUserManager.getUser().id)
            .observe(owner, Observer { data ->
                data?.let {
                    mView?.displayConsultedPatient(data)
                }
            })

    }

    override fun onTapNext(consultationRequestVO: ConsultationRequestVO) {
        mDoctorModel.deleteConsultationRequestById(consultationRequestVO.id)
            .observe(mLifecycleOwner, Observer {
                }
            )
    }

    override fun onTapSkip(consultationRequestVO: ConsultationRequestVO) {
        mDoctorModel.deleteConsultationRequestById(consultationRequestVO.id)
            .observe(mLifecycleOwner, Observer {
            })
    }

    override fun onTapPostpone(consultationRequestVO: ConsultationRequestVO) {
        mView?.displayPostPoneChooserDialog(consultationRequestVO)
    }

    override fun onTapAccept(consultationRequestVO: ConsultationRequestVO) {
        acceptRequest("accept", 2, consultationRequestVO)
      /*  var notiObj=  prepareNotificationForDoctor(mContext,consultationRequestVO.patient.deviceId,
            MyUserManager.getUser().name.toString(), MyUserManager.getUser().id.toString())
        mDoctorModel.sendNotificationToPatient(notiObj,onSuccess = {
            Log.d("onsuccess", it.success.toString())
        }, onFailure = {
            Log.d("notionFailure", it)
        })*/
    }

    override fun onTapPostponeTime(
        postPoneTime: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        acceptRequest("postpone $postPoneTime", 1, consultationRequestVO)
        var notiObj=  prepareNotification(mContext,consultationRequestVO.patient.deviceId,MyUserManager.getUser().name.toString(),
            MyUserManager.getUser().id.toString(),postPoneTime)
        mDoctorModel.sendNotificationToPatient(notiObj, onSuccess = {}) {}

    }

    override fun onTapMedicalRecord(data: ConsultationChatVO) {
        mView?.displayPatientInfoDialog(data)
    }

    override fun onTapPrescription(data: ConsultationChatVO) {
        mView?.displayPrescriptionDialog(data.id, data.patient?.name.toString(), data.start_consultation_date.toString())

    }

    override fun onTapSendMessage(data: ConsultationChatVO) {
        mView?.nextPageChatRoom(data.id)
    }

    override fun onTapRemark(data: ConsultationChatVO) {
        mDoctorModel.getConsultationChatFromDB(data.id)
            .observe(mLifecycleOwner, Observer { data ->
                data?.let {
                    mView?.displayMedicalCommentDialog(it)
                }
            })
    }

    private fun acceptRequest(
        status: String,
        type: Int,
        consultationRequestVO: ConsultationRequestVO
    ) {
        /*var doctorVo = DoctorVO(
            id = SessionManager.doctor_id.toString(),
            device_id = SessionManager.doctor_device_id.toString(),
            name = SessionManager.doctor_name,
            phone = SessionManager.doctor_phone,
            degree = SessionManager.doctor_degree,
            email = SessionManager.doctor_email,
            biography = SessionManager.doctor_bigraphy,
            photo = SessionManager.doctor_photo,
            specialityname = SessionManager.doctor_specialityname,
            speciality = SessionManager.doctor_speciality
        )*/

        mDoctorModel.acceptRequest(
            status,
            consultationRequestVO.id,
            consultationRequestVO.case_summary,
            consultationRequestVO.patient,
            MyUserManager.getUser(), onSuccess = {}, onFailure = {})


        if (type == 2) {
            mView?.displayPatientInfo(consultationRequestVO.id)
        } else {
            mView?.displayPostponseProcessSuccess()
        }
    }
}