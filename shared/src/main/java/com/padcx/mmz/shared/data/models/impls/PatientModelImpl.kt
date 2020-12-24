package com.padcx.mmz.shared.data.models.impls

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import com.padcx.mmz.shared.data.models.BaseModel
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.vos.*
import com.padcx.mmz.shared.network.CloudFirestoreFirebaseApiImpl
import com.padcx.mmz.shared.network.FirebaseApi
import com.padcx.mmz.shared.network.responses.NotiResponse
import com.padcx.mmz.shared.network.responses.NotificationVO
import com.padcx.mmz.shared.utils.dbOperationResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
object PatientModelImpl : PatientModel, BaseModel() {
    override var mFirebaseApi: FirebaseApi = CloudFirestoreFirebaseApiImpl

    override fun sendNotification(data: NotificationVO) {
       /* mApi.sendFcm(data).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("Notification", it.toString())
            }*/
    }

    override fun saveNewPatientRegistration(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.registerPatientData(
            patientVO,
            onSuccess = {},
            onFailure = { onFailure(it) })
    }

    override fun savePatientToDb(patientVO: PatientVO) {
        mTheDB.patientDao().insertPatientData(patientVO)
    }

    override fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadPhotoToFirebaseStorage(image, onSuccess, onFailure)
    }

    override fun sendBroadcastToDoctor(
        notificationVO: NotificationVO,
        onSuccess: (notiResponse: NotiResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mApi.sendFcm(notificationVO)
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let { data ->
                    onSuccess(it)
                }
            }, {
                onFailure(it.localizedMessage ?: "ERROR MESSAGE")
            })
    }

    override fun sendBroadCastConsultationRequest(
        speciality: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendBroadCastConsultationRequest(speciality,
            questionAnswerList,
            patientVO,
            doctorVO,
            dateTime,
            onSuccess = {

            }, onFailure = { onFailure(it) })
    }

    override fun sendDirectRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkoutMedicine(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getSpecialitiesList(
        onSuccess: (List<SpecialitiesVO>) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getSpeciality(onSuccess = {
            /* mTheDB.specialitiesDao().insertSpecialitiesList(it)*/
            mTheDB.specialitiesDao().deleteSpecialities()
            mTheDB.specialitiesDao().insertSpecialitiesList(it)
        }, onFailure = {
            onError(it)
        })
    }

    override fun getSpecialitiesListFromDB(): LiveData<List<SpecialitiesVO>> {
        return mTheDB.specialitiesDao().getSpecialities()
    }

    override fun getRecentlyConsultedDoctorFromApi(documentId: String) {
        /*  mFirebaseApi.getRecentlyConsultationDoctor(documentId,onSuccess = {
              mTheDB.recentDoctorDao().insertRecentDoctorList(it).dbOperationResult(onSuccess = {result ->
                  Log.d("insert success",result)
              },onFailure = {error ->
                  Log.d("failed",error)
              })
          },onFailure = {})*/
    }

    override fun getRecentlyConsultedDoctorFromDb(): LiveData<List<RecentDoctorVO>> {
        return mTheDB.recentDoctorDao().getRecentDoctor()
    }

    override fun getPatientByEmail(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getPatientByEmail(email,
            onSuccess = {
                mTheDB.patientDao().deleteAllPatientData()
                mTheDB.patientDao().insertPatientData(it)
            }, onFailure = { onError(it) })
    }

    override fun addedPatientInfo(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.updatePatientData(patientVO, onSuccess = {}, onFailure = { onError(it) })
    }

    override fun getPatientByEmailFromDB(email: String): LiveData<PatientVO> {
        return mTheDB.patientDao().getAllPatientDataByEmail(email)
    }

    override fun getConsultationAccepts(
        patientId: String,
        onSuccess: (List<ConsultationRequestVO>) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getBroadcastConsultationRequestByPatient(
            patientId,
            onSuccess = {
                mTheDB.consultationRequestDao().deleteAllConsultationRequestData()
                mTheDB.consultationRequestDao().insertConsultationRequestData(it)
            }, onFailure =
            { onError(it) })
    }

    override fun getConsultationAcceptsFromDB(): LiveData<List<ConsultationRequestVO>> {
        return mTheDB.consultationRequestDao().getConsultationAcceptData("accept")
    }

    override fun getConsultationChat(
        consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultationChatById(consultationId,
            onSuccess = {
                mTheDB.consultationChatDao().deleteAllConsultationChatData()
                mTheDB.consultationChatDao().insertConsultationChatData(it)
            }, onFailure = { onError(it) })
    }

    override fun getConsultationChatFromDB(consultationId: String): LiveData<ConsultationChatVO> {
        return mTheDB.consultationChatDao().getAllConsultationChatDataByConsultedId(consultationId)
    }

    override fun getChatMessage(
        consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        DoctorModelImpl.mFirebaseApi.getAllChatMessage(consultationId, onSuccess = {
            mTheDB.chatMessageDao().deleteAllChatMessageData()
            mTheDB.chatMessageDao().insertChatMessages(it)
        }, onFailure = {})
    }

    override fun getAllChatMessageFromDB(): LiveData<List<ChatMessageVO>> {
        return mTheDB.chatMessageDao().getAllChatMessage()
    }

    override fun sendChatMessage(
        messageVO: ChatMessageVO,
        consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.sendMessage(consultationId, messageVO, onSuccess = {}, onFailure = {})
    }

    override fun joinedChatRoomPatient(
        consultation_chatId: String,
        consultationRequestVO: ConsultationRequestVO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.startConsultationChatPatient(
            consultation_chatId,
            consultationRequestVO,
            onSuccess = {}, onFailure =
            { onError(it) })
    }

    override fun getPrescription(
        consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getPrescription(consultationId, onSuccess = {
            mTheDB.prescriptionDao().deleteAllPrescriptionData()
            mTheDB.prescriptionDao().insertPrescriptionList(it)
        }, onFailure = {})
    }

    override fun getPrescriptionFromDB(): LiveData<List<PrescriptionVO>> {
        return mTheDB.prescriptionDao().getAllPrescriptionData()
    }

    override fun getConsultationChatByPatientId(
        patientId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultationChatByPatientId(patientId,
            onSuccess = {
                mTheDB.consultationChatDao().deleteAllConsultationChatData()
                mTheDB.consultationChatDao().insertConsultationChatData(it)
            }, onFailure = { onError(it) })
    }

    override fun getConsultationChatByPatientIdFromDB(patientId: String): LiveData<List<ConsultationChatVO>> {
        return mTheDB.consultationChatDao().getAllConsultationChatDataByPatientId(patientId)
    }

    override fun getRecentDoctors(
        patientId: String,
        onSuccess: (List<RecentDoctorVO>) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getRecentlyConsultedDoctor(
            patientId,
            onSuccess = {
                mTheDB.recentDoctorDao().deleteAllRecentDoctorData()
                mTheDB.recentDoctorDao().insertRecentDoctorList(it)
            }, onFailure = { onError(it) })
    }

    override fun getRecentDoctorsFromDB(): LiveData<List<RecentDoctorVO>> {
        return mTheDB.recentDoctorDao().getAllRecentDoctorData()
    }

    override fun checkout(
        prescriptionList: List<PrescriptionVO>,
        delivery_address: String,
        doctorVO: DoctorVO,
        patientVO: PatientVO,
        total_price: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.checkoutMedicine(
            prescriptionList,
            delivery_address,
            doctorVO,
            patientVO,
            total_price,
            onSuccess = {},
            onFailure = {})

    }


    override fun getSpecialQuestionBySpeciality(
        speciality: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getSpecialQuestionsBySpeciality(speciality,
            onSuccess = {
                mTheDB.specialQuestionDao().deleteSpecialQuestions()
                mTheDB.specialQuestionDao().insertSpecialQuestions(it)
            }, onFailure =
            { onFailure(it) })
    }

    override fun getSpecialQuestionBySpecialityFromDb(speciality: String): LiveData<List<SpecialQuestionVO>> {
        return mTheDB.specialQuestionDao().getAllSpecialQuestionsData()
    }

}