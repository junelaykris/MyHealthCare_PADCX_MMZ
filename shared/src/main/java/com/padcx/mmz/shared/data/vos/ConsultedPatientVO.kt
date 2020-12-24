package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
@Entity(tableName ="consulted_patient")
@IgnoreExtraProperties
class ConsultedPatientVO(
    @PrimaryKey
    var id: String= "",
    var patient_id: String ?= "",
    var doctor_id: String ?= ""
)