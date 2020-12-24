package com.padcx.mmz.shared.data.models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.padcx.mmz.shared.data.vos.*
import com.padcx.mmz.shared.network.FirebaseApi
import com.padcx.mmz.shared.network.responses.NotiResponse
import com.padcx.mmz.shared.network.responses.NotificationVO

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface PatientModel {
    var mFirebaseApi: FirebaseApi

    fun sendNotification(data: NotificationVO)

    //Patient
    fun saveNewPatientRegistration(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun savePatientToDb(patientVO: PatientVO)

    fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendBroadcastToDoctor(
        notificationVO: NotificationVO,
        onSuccess: (notiResponse: NotiResponse) -> Unit,
        onFailure: (String) -> Unit
    )


    fun sendBroadCastConsultationRequest(
        speciality: String,
        caseSummary: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        dateTime: String,
        onSuccess: () -> Unit, onFailure: (String) -> Unit
    )

    fun sendDirectRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun checkoutMedicine(onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun getSpecialQuestionBySpeciality(
        documentName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSpecialQuestionBySpecialityFromDb(speciality: String): LiveData<List<SpecialQuestionVO>>

    //Speciality
    fun getSpecialitiesList(onSuccess: (List<SpecialitiesVO>) -> Unit, onError: (String) -> Unit)
    fun getSpecialitiesListFromDB(): LiveData<List<SpecialitiesVO>>

    //recent doctors
    fun getRecentlyConsultedDoctorFromApi(documentId: String)
    fun getRecentlyConsultedDoctorFromDb(): LiveData<List<RecentDoctorVO>>

    fun getPatientByEmail(
        patientId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun addedPatientInfo(patientVO: PatientVO, onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getPatientByEmailFromDB(email: String): LiveData<PatientVO>


    ///acceptConsultation
    fun getConsultationAccepts(
        patientId: String,
        onSuccess: (List<ConsultationRequestVO>) -> Unit,
        onError: (String) -> Unit
    )

    fun getConsultationAcceptsFromDB(): LiveData<List<ConsultationRequestVO>>

    fun getConsultationChat(
        consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun getConsultationChatFromDB(consultationId: String): LiveData<ConsultationChatVO>

    fun getChatMessage(consultationId: String, onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getAllChatMessageFromDB(): LiveData<List<ChatMessageVO>>

    fun sendChatMessage(
        messageVO: ChatMessageVO, consultationId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )


    fun joinedChatRoomPatient(
        consultation_chat_id: String, consultationRequestVO: ConsultationRequestVO,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )


    fun getPrescription(consultationId: String, onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getPrescriptionFromDB(): LiveData<List<PrescriptionVO>>

    fun getConsultationChatByPatientId(
        patientId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun getConsultationChatByPatientIdFromDB(patientId: String): LiveData<List<ConsultationChatVO>>

    fun getRecentDoctors(
        patientId: String,
        onSuccess: (List<RecentDoctorVO>) -> Unit,
        onError: (String) -> Unit
    )

    fun getRecentDoctorsFromDB(): LiveData<List<RecentDoctorVO>>

    fun checkout(
        prescriptionList: List<PrescriptionVO>,
        delivery_address: String,
        doctorVO: DoctorVO,
        patientVO: PatientVO,
        total_price: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

}