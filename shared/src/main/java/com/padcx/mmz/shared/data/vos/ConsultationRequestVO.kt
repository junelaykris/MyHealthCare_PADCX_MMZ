package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padcx.mmz.shared.persistance.typeconverters.CaseSummaryTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.DoctorVOTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.PatientVOTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.TimeStampTypeConverter

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "consultation_request")
@IgnoreExtraProperties
@TypeConverters(
        CaseSummaryTypeConverter::class,
        PatientVOTypeConverter::class,
        DoctorVOTypeConverter::class
)
data class ConsultationRequestVO(
        @PrimaryKey
        var id: String = "",
        var speciality: String? = "",
        var date_time: String? = "",
        var patient: PatientVO,
        var doctor_info: DoctorVO,
        var case_summary: ArrayList<QuestionAnswerVO> = arrayListOf(),
        var status: String? = "none",
        var patient_id: String? = "",
        var consultation_id: String? = ""
)