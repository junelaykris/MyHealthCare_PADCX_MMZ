package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padcx.mmz.shared.persistance.typeconverters.DeliveryRoutineTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.DoctorVOTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.PatientVOTypeConverter
import com.padcx.mmz.shared.persistance.typeconverters.PrescriptionTypeConverters

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "checkout")
@IgnoreExtraProperties
@TypeConverters(
        PrescriptionTypeConverters::class,
        DoctorVOTypeConverter::class,
        PatientVOTypeConverter::class,
        DeliveryRoutineTypeConverter::class)
data class CheckoutVO(
        @PrimaryKey
        var id: String= "",
        var delivery_address: String ?= "",
        var total_price : Int? =0,
        var patientVO: PatientVO ?=PatientVO(),
        var doctorVO: DoctorVO ?=DoctorVO(),
        var delivery_routine : DeliveryRoutineVO ?= DeliveryRoutineVO(),
        var prescription : ArrayList<PrescriptionVO> = arrayListOf()
)
