package com.padcx.mmz.shared.network

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.data.vos.*
import com.padcx.mmz.shared.network.responses.DataVO
import com.padcx.mmz.shared.network.responses.NotificationVO
import com.padcx.mmz.shared.utils.*
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
object CloudFirestoreFirebaseApiImpl : FirebaseApi {

    val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    private val mPatientModel: PatientModel = PatientModelImpl


    override fun uploadPhotoToFirebaseStorage(
        image: Bitmap,
        onSuccess: (photoUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            onFailure("Update Profile Failed")
        }.addOnSuccessListener { taskSnapshot ->
            Log.d(ContentValues.TAG, "User profile updated.")
        }


        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            val imageUrl = task.result?.toString()
            imageUrl?.let { onSuccess(it) }
        }
    }

    override fun registerPatientData(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(PATIENTS)
            .document(patientVO?.id)
            .set(patientVO)
            .addOnSuccessListener { Log.d("Success", "Successfully") }
            .addOnFailureListener { Log.d("Failure", "Failed ") }

    }

    override fun saveMedicalRecord(
        consultationChatVO: ConsultationChatVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(consultation_chat)
            .document(consultationChatVO.id)
            .set(consultationChatVO)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun getPatientByEmail(
        email: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(PATIENTS)
            .whereEqualTo("email", email)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<PatientVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<PatientVO>(Data, PatientVO::class.java)
                        list.add(docsData)
                    }
                    onSuccess(list[0])
                }
            }
    }

    override fun getDoctorByEmail(
        email: String,
        onSuccess: (doctorVO: DoctorVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(DOCTORS)
            .whereEqualTo("email", email)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<DoctorVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<DoctorVO>(Data, DoctorVO::class.java)
                        list.add(docsData)
                    }
                    list?.let {
                        if (list.size > 0) {
                            onSuccess(list[0])
                        }
                    }
                }
            }
    }

    override fun registerDoctorData(
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(DOCTORS)
            .document(doctorVO.id)
            .set(doctorVO)
            .addOnSuccessListener { Log.d("Success", "Successfully") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun getSpeciality(
        onSuccess: (specialities: List<SpecialitiesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("specialities")
            .get()
            .addOnSuccessListener { result ->
                val specialitiesList: MutableList<SpecialitiesVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id)
                    val Data = Gson().toJson(hashmap)
                    val docData = Gson().fromJson<SpecialitiesVO>(Data, SpecialitiesVO::class.java)
                    specialitiesList.add(docData)
                }
                onSuccess(specialitiesList)

            }.addOnFailureListener {
                onFailure(it.message ?: EN_ERROR_MESSAGE)
            }
    }

    override fun getSpecialQuestionsBySpeciality(
        speciality: String,
        onSuccess: (specialQuestionList: List<SpecialQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("specialities/$speciality/spical_question")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val specialQuestionList: MutableList<SpecialQuestionVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData =
                            Gson().fromJson<SpecialQuestionVO>(Data, SpecialQuestionVO::class.java)
                        specialQuestionList.add(docsData)
                    }
                    onSuccess(specialQuestionList)
                }
            }
    }

    override fun startConsultation(
        consulationId: String,
        dateTime: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val id = UUID.randomUUID().toString()

        val consultationChatMap = hashMapOf(
            "case_summary" to questionAnswerList,
            "id" to consulationId,
            "finish_consultation_status" to false,
            "patient_id" to patientVO.id,
            "doctor_id" to doctorVO.id,
            "start_consultation_date" to getCurrentDate(),
            "patient" to patientVO,
            "medical_record" to "",
            "doctor_info" to doctorVO
        )

        db.collection(consultation_chat)
            .document(consulationId)
            .set(consultationChatMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }


        for (item in questionAnswerList) {
            db.collection("$PATIENTS/${patientVO.id}/$general_questions")
                .document(item.id)
                .set(item)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }
        }


        val consultationRequestMap = hashMapOf(
            "status" to "accept",
            "patient_id" to patientVO.id,
            "doctor_info" to doctorVO,
            "speciality" to doctorVO.speciality,
            "patient" to patientVO,
            "case_summary" to questionAnswerList,
            "consultation_id" to consulationId
        )
        db.collection(consultation_request)
            .document(consulationId)
            .set(consultationRequestMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }


        val consultedPatientId = UUID.randomUUID().toString()
        val consultedPatientMap = hashMapOf(
            "id" to consultedPatientId,
            "patient_id" to patientVO.id
        )
        db.collection("$doctors/${doctorVO.id}/$consulted_patient")
            .document(consultedPatientId)
            .set(consultedPatientMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }

    }

    override fun getConsultationChat(
        patientId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(CONSULTATION_CHAT)
            .whereEqualTo("patient_id", patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                } ?: run {
                    val consultationChatList: MutableList<ConsultationChatVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<ConsultationChatVO>(
                            Data,
                            ConsultationChatVO::class.java
                        )
                        consultationChatList.add(docData)
                    }
                    onSuccess(consultationChatList)
                }
            }
    }

    override fun getAllChatMessage(
        documentId: String,
        onSuccess: (List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$consultation_chat/$documentId/$chat_message")
            .orderBy("sendAt")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ChatMessageVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData =
                            Gson().fromJson<ChatMessageVO>(Data, ChatMessageVO::class.java)
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun sendMessage(
        consultationId: String,
        messageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uuid = UUID.randomUUID()
        val chatMessageMap = hashMapOf(
            "id" to uuid.toString(),
            "messageText" to messageVO.messageText,
            "messageImage" to messageVO.messageImage,
            "sentBy" to messageVO.sendBy,
            "sendAt" to messageVO.sendAt,
            "type" to messageVO.type
        )

        db.collection("$consultation_chat/$consultationId/$chat_message")
            .document(uuid.toString())
            .set(chatMessageMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }

        /*  val uuid = UUID.randomUUID()
          val messageMap = hashMapOf(
              "id" to uuid.toString(),
              "messageText" to messageVO.messageText,
              "messageImage" to messageVO.messageImage,
              "sentBy" to messageVO.sendBy,
              "sendAt" to messageVO.sendAt

          )
          db.collection("$CONSULTATION_CHAT/${documentId}/$MESSAGE")
              .document(uuid.toString())
              .set(messageMap)
              .addOnSuccessListener {
                  onSuccess()
              }
              .addOnFailureListener {
                  onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
              }*/
    }

    override fun getSpecialQuestionBySpecialities(
        documentName: String,
        onSuccess: (List<SpecialQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(
            "$SPECIALITIES/$documentName/$SPECIAL_QUESTION_NODE"
        )
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check internet connection")
                } ?: run {
                    val specialQuestionList: MutableList<SpecialQuestionVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData =
                            Gson().fromJson<SpecialQuestionVO>(Data, SpecialQuestionVO::class.java)
                        specialQuestionList.add(docData)
                    }
                    onSuccess(specialQuestionList)
                }
            }
    }

    override fun sendBroadCastConsultationRequest(
        speciality: String,
        caseSummary: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uuid = UUID.randomUUID().toString()

        val consultationReqMap = hashMapOf(
            "case_summary" to caseSummary,
            "id" to uuid,
            "patient" to patientVO,
            "doctor_info" to doctorVO,
            "speciality" to speciality,
            "date_time" to dateTime,
            "status" to "none"
        )
        /* val consultationReqMap = hashMapOf(
             "id" to uuid,
             "case_summary" to caseSummary,
             "patient" to patientVO,
             "doctor_info" to doctorVO,
             "speciality" to speciality,
             "date_time" to dateTime,
             "status" to "none"
         )*/

        db.collection(CONSULTATION_REQUEST)
            .document(uuid.toString())
            .set(consultationReqMap)
            .addOnSuccessListener {
                Log.d("success", "Successfully add consultation req")
                onSuccess()
            }.addOnFailureListener {
                Log.d("onFailure", "Failed to add consultation req")
                onFailure("Failed to add doctor")
            }

        db.collection(PATIENTS)
            .document(patientVO.id)
            .set(patientVO)
            .addOnSuccessListener { Log.d("Success", "Successfully") }
            .addOnFailureListener { Log.d("Failure", "Failed ") }

    }

    override fun sendDirectRequest(
        caseSummary: QuestionAnswerVO,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val directConsultationMap = hashMapOf(
            "case_summary" to caseSummary,
            "patient" to patientVO,
            "doctor" to doctorVO,
            "date_time" to dateTime
        )
    }

    override fun checkoutMedicine(
        prescriptionList: List<PrescriptionVO>,
        deliveryAddressVO: String,
        doctorVO: DoctorVO,
        patientVO: PatientVO,
        total_price: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val deliveryRoutineVO = DeliveryRoutineVO("1", getDayAgo(2).toString())

        val id = UUID.randomUUID().toString()
        val checkoutVOMap = hashMapOf(
            "id" to id,
            "patient_info" to patientVO,
            "doctor_info" to doctorVO,
            "delivery_address" to deliveryAddressVO,
            "delivery_routine" to deliveryRoutineVO,
            "prescription" to prescriptionList,
            "total_price" to total_price
        )

        db.collection("$CHECKOUT")
            .document(id)
            .set(checkoutVOMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun getRecentlyConsultationDoctor(
        documentId: String,
        onSuccess: (List<RecentDoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$PATIENT/${documentId}/$RECENTLY_DOCTOR")
            .get()
            .addOnSuccessListener { result ->
                val recentlyDoctorList: MutableList<RecentDoctorVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id)
                    val Data = Gson().toJson(hashmap)
                    val docData = Gson().fromJson<RecentDoctorVO>(Data, RecentDoctorVO::class.java)
                    recentlyDoctorList.add(docData)
                }
                onSuccess(recentlyDoctorList)

            }.addOnFailureListener {
                onFailure(it.message ?: EN_ERROR_MESSAGE)
            }
    }

    override fun acceptRequest(
        status: String,
        consulationId: String,
        questionAnswerList: List<QuestionAnswerVO>,
        patient: PatientVO,
        doctor: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultationRequestMap = hashMapOf(
            "status" to status,
            "doctor_id" to doctor.id,
            "patient_id" to patient.id,
            "doctor_info" to doctor,
            "speciality" to doctor.speciality,
            "patient" to patient,
            "case_summary" to questionAnswerList,
            "consultation_id" to "",
        )
        db.collection(consultation_request)
            .document(consulationId)
            .set(consultationRequestMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }

    }

    override fun finishConsultation(
        consultationChatVO: ConsultationChatVO,
        prescriptionList: List<PrescriptionVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        //add recent doctor in Patient
        consultationChatVO.doctor_info?.let {

            db.collection("$PATIENTS/${consultationChatVO.patient_id}/$recent_doctors")
                .document(consultationChatVO.doctor_id.toString())
                .set(it)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }
        }


        // update finish conversation status
        val consultationChatMap = hashMapOf(
            "finish_consultation_status" to true,
            "id" to consultationChatVO.id,
            "patient_id" to consultationChatVO.patient_id,
            "doctor_id" to consultationChatVO.doctor_id,
            "case_summary" to consultationChatVO.case_summary,
            "patient" to consultationChatVO.patient,
            "start_consultation_date" to consultationChatVO.start_consultation_date,
            "medical_record" to consultationChatVO.medical_record,
            "doctor_info" to consultationChatVO.doctor_info
        )

        db.collection("$consultation_chat")
            .document(consultationChatVO.id)
            .set(consultationChatMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }


        // add prescription
        for (item in prescriptionList) {
            db.collection("$consultation_chat/${consultationChatVO.id}/$prescription")
                .document(item.id)
                .set(item)
                .addOnSuccessListener { Log.d("Success", "Successfully ") }
                .addOnFailureListener { Log.d("Failure", "Failed") }
        }
    }


    override fun prescribeMedicine(
        documentId: String,
        medicine: PrescriptionVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uuid = UUID.randomUUID()
        val prescriptionMap = hashMapOf(
            "id" to uuid.toString(),
            "medicine_name" to medicine,
            "routine" to medicine.routineVO,
        )
        db.collection("$CONSULTATION_CHAT/${documentId}/$PRESCRIPTION")
            .document(uuid.toString())
            .set(prescriptionMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
            }
    }

    override fun getGeneralQuestion(
        onSuccess: (List<GeneralQuestionTemplateVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$GENERAL_QUESTION_TEMPLATE")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<GeneralQuestionTemplateVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<GeneralQuestionTemplateVO>(
                            Data,
                            GeneralQuestionTemplateVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun getAllMedicine(
        speciality: String,
        onSuccess: (List<MedicineVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$SPECIALITIES/$speciality/$MEDICINE")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<MedicineVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<MedicineVO>(Data, MedicineVO::class.java)
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }


    override fun getConsultationRequest(
        consultationRequestId: String,
        onSuccess: (consultationRequest: ConsultationRequestVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(CONSULTATION_REQUEST)
            .whereEqualTo("id", consultationRequestId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
                        list.add(docsData)
                    }
                    list?.let {
                        if (list.size > 0) {
                            onSuccess(list[0])
                        }
                    }

                }
            }
    }

    override fun getPrescription(
        consultationId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$consultation_chat/$consultationId/$prescription")
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<PrescriptionVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<PrescriptionVO>(Data, PrescriptionVO::class.java)
                    list.add(docsData)
                }
                onSuccess(list)
            }
    }


    override fun getPrescriptionMedicine(
        consultationId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$consultation_chat/$consultationId/$prescription")
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<PrescriptionVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id.toString())
                    val Data = Gson().toJson(hashmap)
                    val docsData = Gson().fromJson<PrescriptionVO>(Data, PrescriptionVO::class.java)
                    list.add(docsData)
                }
                onSuccess(list)
            }
    }


    override fun getBroadcastConsultationRequestBySpeciality(
        speciality: String,
        onSuccess: (list: List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(consultation_request)
            .whereEqualTo("speciality", speciality)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun getConsultationChatForDoctor(
        doctorId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$CONSULTATION_CHAT")
            .whereEqualTo("doctor_id", doctorId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationChatVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationChatVO>(
                            Data,
                            ConsultationChatVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun getConsultationChatById(
        consultedId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$CONSULTATION_CHAT")
            .whereEqualTo("id", consultedId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationChatVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationChatVO>(
                            Data,
                            ConsultationChatVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun getBroadcastConsultationRequestByPatient(
        patientId: String,
        onSuccess: (consultationRequest: List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(consultation_request)
            .whereEqualTo("patient_id", patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationRequestVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun startConsultationChatPatient(
        chatId: String,
        consultationRequestVO: ConsultationRequestVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultationRequestMap = hashMapOf(
            "status" to "complete",
            "speciality" to consultationRequestVO.doctor_info.speciality,
            "doctor_id" to consultationRequestVO.doctor_info.id,
            "patient_id" to consultationRequestVO.patient.id,
            "doctor_info" to consultationRequestVO.doctor_info,
            "patient" to consultationRequestVO.patient,
            "case_summary" to consultationRequestVO.case_summary,
            "consultation_id" to chatId
        )
        db.collection(consultation_request)
            .document(consultationRequestVO.id)
            .set(consultationRequestMap)
            .addOnSuccessListener { Log.d("Success", "Successfully ") }
            .addOnFailureListener { Log.d("Failure", "Failed") }
    }

    override fun getConsultedPatient(
        doctorId: String,
        onSuccess: (List<ConsultedPatientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$doctors/$doctorId/$consulted_patient")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultedPatientVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultedPatientVO>(
                            Data,
                            ConsultedPatientVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun getConsultationChatByPatientId(
        patientId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$consultation_chat")
            .whereEqualTo("patient_id", patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<ConsultationChatVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<ConsultationChatVO>(
                            Data, ConsultationChatVO::class.java
                        )
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun getRecentlyConsultedDoctor(
        patientId: String,
        onSuccess: (doctorList: List<RecentDoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$PATIENTS/$patientId/$recent_doctors")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<RecentDoctorVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData =
                            Gson().fromJson<RecentDoctorVO>(Data, RecentDoctorVO::class.java)
                        list.add(docsData)
                    }
                    onSuccess(list)
                }
            }
    }

    override fun updatePatientData(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(PATIENTS)
            .document(patientVO.id)
            .set(patientVO)
            .addOnSuccessListener {
                Log.d("Success", "Successfully") }
            .addOnFailureListener {
                Log.d("Failure", "Failed ") }
    }

}
