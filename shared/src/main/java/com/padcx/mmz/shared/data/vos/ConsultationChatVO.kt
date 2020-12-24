package com.padcx.mmz.shared.data.vos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padcx.mmz.shared.persistance.typeconverters.CaseSummaryTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.DoctorVOTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.PatientVOTypeConverter

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "consultation_chat")
@IgnoreExtraProperties
@TypeConverters(CaseSummaryTypeConverter::class, PatientVOTypeConverter::class, DoctorVOTypeConverter::class)
data class ConsultationChatVO(
        @PrimaryKey
        var id: String= "",
        var doctor_id : String ? ="",
        var patient_id : String ?= "",
        var finish_consultation_status : Boolean =false,
        var patient : PatientVO ? =null ,
        var doctor_info : DoctorVO ? = DoctorVO(),
        var start_consultation_date : String ?= "",
        var medical_record : String ?= "",
        var case_summary : ArrayList<QuestionAnswerVO>? = arrayListOf()
)