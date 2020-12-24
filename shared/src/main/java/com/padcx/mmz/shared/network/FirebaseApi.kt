package com.padcx.mmz.shared.network

import android.graphics.Bitmap
import com.padcx.mmz.shared.data.vos.*

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
interface FirebaseApi {

    fun uploadPhotoToFirebaseStorage(image : Bitmap, onSuccess: (photoUrl : String) -> Unit, onFailure: (String) -> Unit)

    fun registerDoctorData(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun registerPatientData(patientVO: PatientVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun saveMedicalRecord(consultationChatVO: ConsultationChatVO ,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun getPatientByEmail(patientId : String ,
                   onSuccess: (patientVO: PatientVO) -> Unit,
                   onFailure: (String) -> Unit)

    fun getDoctorByEmail(doctorId : String ,
                          onSuccess: (doctorVO: DoctorVO) -> Unit,
                          onFailure: (String) -> Unit)

  /*  fun getDoctorList(onSuccess: (doctorList: List<DoctorVO>) -> Unit, onFailure: (String) -> Unit)
    fun getPatientList(onSuccess: (patientList: List<PatientVO>)-> Unit, onFailure: (String) -> Unit)*/

    fun getSpeciality(onSuccess: (specialities:List<SpecialitiesVO>) -> Unit, onFailure:(String)->Unit)

    fun getSpecialQuestionsBySpeciality(
        speciality : String,
        onSuccess: (specialQuestionList : List<SpecialQuestionVO>) -> Unit,
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


   /* fun startConsultation(caseSummary: List<QuestionAnswerVO>,
                          doctorVO: DoctorVO,
                          patientVO: PatientVO,
                          dateTime: String,
                          onSuccess: (currentDocumentId:String) -> Unit,
                          onFailure: (String) -> Unit)
*/

    fun getConsultationChat(patientId: String, onSuccess: (List<ConsultationChatVO>) -> Unit, onFailure: (String) -> Unit)

    fun getAllChatMessage(documentId: String,onSuccess: (List<ChatMessageVO>) -> Unit,onFailure: (String) -> Unit)

    fun sendMessage(documentId:String,messageVO: ChatMessageVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun getSpecialQuestionBySpecialities(documentName:String,onSuccess: (List<SpecialQuestionVO>) -> Unit,onFailure: (String) -> Unit)


    fun sendBroadCastConsultationRequest(
            speciality:String,
            caseSummary: List<QuestionAnswerVO>,
            patientVO: PatientVO,
            doctorVO:DoctorVO,
            dateTime : String,
            onSuccess: () -> Unit, onFailure: (String) -> Unit)


    fun sendDirectRequest(
            caseSummary: QuestionAnswerVO,
            patientVO: PatientVO,
            doctorVO: DoctorVO,
            dateTime: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit)

    fun checkoutMedicine(prescriptionList : List<PrescriptionVO>,deliveryAddressVO: String,
                         doctorVO: DoctorVO, patientVO: PatientVO , total_price : String,
                         onSuccess: () -> Unit,onFailure: (String) -> Unit)


    fun getRecentlyConsultationDoctor(documentId:String,onSuccess: (List<RecentDoctorVO>) -> Unit,onFailure: (String) -> Unit)

    fun acceptRequest(
        status : String,
        consultationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit)

    fun finishConsultation(consultationChatVO: ConsultationChatVO, prescriptionList : List<PrescriptionVO>, onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun prescribeMedicine(documentId: String,medicine:PrescriptionVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)

   /* fun getGeneralQuestion(onSuccess: (List<GeneralQuestionVO>) -> Unit,onFailure: (String) -> Unit)*/
   fun getGeneralQuestion(onSuccess: (List<GeneralQuestionTemplateVO>) -> Unit, onFailure: (String) -> Unit)

    fun getPrescriptionMedicine(documentId: String,onSuccess: (List<PrescriptionVO>) -> Unit,onFailure: (String) -> Unit)

    fun getAllMedicine(speciality: String,onSuccess: (List<MedicineVO>) -> Unit,onFailure: (String) -> Unit)

    fun getConsultationRequest(consultationRequestId: String,onSuccess: (ConsultationRequestVO) -> Unit,onFailure: (String) -> Unit)

    fun getPrescription(consultationId: String ,onSuccess: (List<PrescriptionVO>) -> Unit,onFailure: (String) -> Unit)


    fun getBroadcastConsultationRequestBySpeciality(
        speciality : String,
        onSuccess: (list : List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getConsultationChatForDoctor(doctorId: String ,onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)

    fun getConsultationChatById(consulationId : String ,onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)

    fun getBroadcastConsultationRequestByPatient(
        patientId :String,
        onSuccess: (consultationRequest : List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun startConsultationChatPatient(chatId: String, consultationRequestVO: ConsultationRequestVO ,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getConsultedPatient(doctorId : String,onSuccess: (List<ConsultedPatientVO>) -> Unit,onFailure: (String) -> Unit)

    fun getConsultationChatByPatientId(patientId : String ,onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)
    fun getRecentlyConsultedDoctor(patientId : String, onSuccess: (doctorList : List<RecentDoctorVO>) -> Unit,onFailure: (String) -> Unit)

    fun updatePatientData(patientVO: PatientVO ,onSuccess: () -> Unit,
                          onFailure: (String) -> Unit
    )

}