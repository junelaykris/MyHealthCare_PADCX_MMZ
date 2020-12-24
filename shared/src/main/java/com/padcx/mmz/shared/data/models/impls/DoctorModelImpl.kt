package com.padcx.mmz.shared.data.models.impls

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padcx.mmz.shared.data.models.BaseModel
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.vos.*

import com.padcx.mmz.shared.network.CloudFirestoreFirebaseApiImpl
import com.padcx.mmz.shared.network.FirebaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.padcx.mmz.shared.network.responses.NotiResponse
import com.padcx.mmz.shared.network.responses.NotificationVO

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
object DoctorModelImpl : DoctorModel, BaseModel() {

    override var mFirebaseApi: FirebaseApi = CloudFirestoreFirebaseApiImpl

    override fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadPhotoToFirebaseStorage(image, onSuccess, onFailure)
    }

    override fun saveNewDoctorRegistration(
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.registerDoctorData(
            doctorVO,
            onSuccess = {},
            onFailure = { onFailure(it) })
    }

    override fun sendNotificationToPatient(   // For Doctor App SIde
        notificationVO: NotificationVO,
        onSuccess: (notiResponse: NotiResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mApi.sendFcm(notificationVO)
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let { data -> onSuccess(it)
                }
            }, {
                onFailure(it.localizedMessage ?: "ERROR MESSAGE")
            })
    }

    override fun saveMedicalRecord(
        consultationChatVO: ConsultationChatVO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.saveMedicalRecord(consultationChatVO, onSuccess = {
        }, onFailure = {})
    }

    override fun acceptRequest(
        status: String,
        consultationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.acceptRequest(status, consultationId, questionAnswerList, patientVO, doctorVO,
            onSuccess = {}, onFailure = { onFailure(it) })
    }

    override fun startConsultation(
        consultationId: String,
        dateTime: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.startConsultation(consultationId,
            dateTime,
            questionAnswerList,
            patientVO,
            doctorVO,
            onSuccess = {},
            onFailure = { onFailure(it) })
    }

    override fun getConsultationByConsultationRequestId(
        requestId: String,
        onSuccess: (consultationRequestVO: ConsultationRequestVO) -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultationRequest(requestId,
            onSuccess = {
                mTheDB.consultationRequestDao().deleteAllConsultationRequestData()
                mTheDB.consultationRequestDao().insertConsultationRequest(it)
            }, onFailure = { onError(it) })
    }

    override fun getConsultationByConsultationRequestIdFromDB(requestId: String): LiveData<ConsultationRequestVO> {
        return mTheDB.consultationRequestDao()
            .getConsultationRequestByConsultationRequestId(requestId)
    }

    override fun getConsultationChat(
        consultedId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultationChatById(consultedId,
            onSuccess = {
                mTheDB.consultationChatDao().deleteAllConsultationChatData()
                mTheDB.consultationChatDao().insertConsultationChatData(it)
            }, onFailure = { onError(it) })
    }

    override fun getConsultationChatFromDB(consultedId: String): LiveData<ConsultationChatVO> {
        return mTheDB.consultationChatDao().getAllConsultationChatDataByConsultedId(consultedId)
    }

    override fun getConsultationByDoctorId(
        doctorId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultationChatForDoctor(doctorId,
            onSuccess = {
                mTheDB.consultationChatDao().deleteAllConsultationChatData()
                mTheDB.consultationChatDao().insertConsultationChatData(it)

            }, onFailure = { onError(it) })
    }

    override fun getConsultationByDoctorIdFromDB(doctorId: String): LiveData<List<ConsultationChatVO>> {
        return mTheDB.consultationChatDao().getAllConsultationChatDataByDoctorId(doctorId)
    }

    override fun getConsultedPatient(
        doctorId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getConsultedPatient(doctorId, onSuccess = {
            mTheDB.consultedPatientDao().deleteConsultedPatient()
            mTheDB.consultedPatientDao().insertConsultedPatient(it)
        }, onFailure = {})
    }

    override fun getConsultedPatientFromDB(id: String): LiveData<List<ConsultedPatientVO>> {
        return mTheDB.consultedPatientDao().getConsultedPatient(id)
    }

    override fun deleteConsultationRequestById(Id: String): LiveData<List<ConsultationRequestVO>> {
        mTheDB.consultationRequestDao().deleteAllConsultationRequestDataById(Id)
        return mTheDB.consultationRequestDao().getAllConsultationRequestDataBySpeciality("skin specialist")
    }


    override fun finishConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun finishConsultation(
        consultationChatVO: ConsultationChatVO,
        prescriptionList: List<PrescriptionVO>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.finishConsultation(consultationChatVO,prescriptionList, onSuccess = {
        }, onFailure = {})
    }

    override fun prescribeMedicine(
        medicine: MedicineVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun saveDoctorToDb(doctorVO: DoctorVO) {
        mTheDB.doctorDao().insertDoctorData(doctorVO)
    }

    override fun getBroadCastConsultationRequest(
        speciality: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getBroadcastConsultationRequestBySpeciality(speciality,
            onSuccess = {
                mTheDB.consultationRequestDao().deleteAllConsultationRequestData()
                mTheDB.consultationRequestDao().insertConsultationRequestData(it)

            }, onFailure = { onError(it) })
    }

    override fun getBroadcastConsultationRequestsFromDB(speciality: String): LiveData<List<ConsultationRequestVO>> {

        return mTheDB.consultationRequestDao().getAllConsultationRequestDataBySpeciality(speciality)
        //return mTheDB.consultationRequestDao().getAllConsultationRequestDataBySpeciality

    }

    override fun sendChatMessage(
        messageVO: ChatMessageVO,
        chatId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.sendMessage(chatId, messageVO, onSuccess = {}, onFailure = {})
    }

    override fun getChatMessage(
        consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getAllChatMessage(consultationId, onSuccess = {
            mTheDB.chatMessageDao().deleteAllChatMessageData()
            mTheDB.chatMessageDao().insertChatMessages(it)
        }, onFailure = {})
    }

    override fun getAllChatMessageFromDB(): LiveData<List<ChatMessageVO>> {
        return mTheDB.chatMessageDao().getAllChatMessage()
    }

    override fun getGeneralQuestionTemplate(onSuccess: () -> Unit, onError: (String) -> Unit) {
        mFirebaseApi.getGeneralQuestion( onSuccess = {
            mTheDB.generalQuestionTemplateDao().deleteAllGeneralQuestionTemplate()
            mTheDB.generalQuestionTemplateDao().insertGeneralQuestionTemplateList(it)
        }, onFailure = {})
    }

    override fun getGeneralQuestionTemplateFromDB(): LiveData<List<GeneralQuestionTemplateVO>> {
        return mTheDB.generalQuestionTemplateDao().getAllGeneralQuestionTemplateData()
    }

    override fun getAllMedicine(
        speciality: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getAllMedicine(speciality,  onSuccess = {
            mTheDB.medicineDao().deleteAllMedicine()
            mTheDB.medicineDao().insertMedicalDataList(it)
        }, onFailure = {})
    }

    override fun getAllMedicineFromDB(): LiveData<List<MedicineVO>> {
        return mTheDB.medicineDao().getAllMedicine()
    }

    override fun getPrescription(
        consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseApi.getPrescription(consultationId,  onSuccess = {
            mTheDB.prescriptionDao().deleteAllPrescriptionData()
            mTheDB.prescriptionDao().insertPrescriptionList(it)
        }, onFailure = {})
    }

    override fun getPrescriptionFromDB(consulation_chat_id: String): LiveData<List<PrescriptionVO>> {
        return mTheDB.prescriptionDao().getAllPrescriptionData(consulation_chat_id)
    }


    override fun getDoctorByEmail(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        mFirebaseApi.getDoctorByEmail(email,
            onSuccess = {
                mTheDB.doctorDao().deleteAllDoctorData()
                mTheDB.doctorDao().insertDoctorData(it)
            }, onFailure = { onError(it) })
    }

    override fun getDoctorByEmailFromDB(email: String): LiveData<DoctorVO> {
        return mTheDB.doctorDao().getAllDoctorDataByEmail(email)
    }


}