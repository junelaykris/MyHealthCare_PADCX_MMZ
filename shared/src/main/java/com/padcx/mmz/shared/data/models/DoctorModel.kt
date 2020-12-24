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
interface DoctorModel {
    var mFirebaseApi : FirebaseApi
    fun uploadPhotoToFirebaseStorage(image : Bitmap, onSuccess: (photoUrl : String) -> Unit, onFailure: (String) -> Unit)
    fun saveNewDoctorRegistration(doctorVO: DoctorVO, onSuccess: () -> Unit, onError: (String) -> Unit)

    fun sendNotificationToPatient(
        notificationVO: NotificationVO,
        onSuccess: (notiResponse: NotiResponse) -> Unit,
        onFailure: (String) -> Unit
    )


    fun saveMedicalRecord(consultationChatVO: ConsultationChatVO, onSuccess: () -> Unit, onError: (String) -> Unit)

    fun acceptRequest(
        status: String,
        consultationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun startConsultation(
        consultationId: String,
        dateTime: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun  getConsultationByConsultationRequestId(requestId : String , onSuccess: (consultationRequestVO :ConsultationRequestVO) -> Unit, onError: (String) -> Unit)

    fun  getConsultationByConsultationRequestIdFromDB(requestId : String) : LiveData<ConsultationRequestVO>



    fun getConsultationChat(consultedId:  String,  onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getConsultationChatFromDB(consultedId : String) : LiveData<ConsultationChatVO>


     fun getConsultationByDoctorId(doctorId: String, onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getConsultationByDoctorIdFromDB(doctorId : String) : LiveData<List<ConsultationChatVO>>

    fun getConsultedPatient(doctorId: String ,
                            onSuccess: () -> Unit,
                            onError: (String) -> Unit)

    fun  getConsultedPatientFromDB(id: String): LiveData<List<ConsultedPatientVO>>

    fun deleteConsultationRequestById(Id : String)  : LiveData<List<ConsultationRequestVO>>


    fun finishConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun prescribeMedicine(medicine: MedicineVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    //DB
    fun saveDoctorToDb(doctorVO: DoctorVO)

    fun getDoctorByEmail( email: String,
                           onSuccess: () -> Unit,
                           onError: (String) -> Unit)

    fun getDoctorByEmailFromDB(email: String) : LiveData<DoctorVO>

    fun getBroadCastConsultationRequest(speciality:String,
                                        onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getBroadcastConsultationRequestsFromDB(speciality: String) : LiveData<List<ConsultationRequestVO>>

    fun sendChatMessage( messageVO: ChatMessageVO , chatId: String ,
                         onSuccess: () -> Unit,
                         onError: (String) -> Unit)

    fun getChatMessage(consultationId: String, onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getAllChatMessageFromDB () : LiveData<List<ChatMessageVO>>

    fun getGeneralQuestionTemplate( onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getGeneralQuestionTemplateFromDB () : LiveData<List<GeneralQuestionTemplateVO>>


    ///Prescription
    fun getAllMedicine(speciality: String ,onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getAllMedicineFromDB() : LiveData<List<MedicineVO>>

    fun finishConsultation( consultationChatVO: ConsultationChatVO , prescriptionList : List<PrescriptionVO> ,onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getPrescription(consultationId : String ,onSuccess: () -> Unit, onError: (String) -> Unit)

    fun getPrescriptionFromDB(consulation_chat_id: String): LiveData<List<PrescriptionVO>>

}