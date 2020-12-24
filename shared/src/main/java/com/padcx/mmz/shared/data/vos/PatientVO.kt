package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padcx.mmz.shared.persistance.typeconverters.AddressConverter
import com.padcx.mmz.shared.persistance.typeconverters.TimeStampTypeConverter
import java.io.Serializable

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@IgnoreExtraProperties
@Entity(tableName = "patient")
@TypeConverters(AddressConverter::class)
data class PatientVO(
        @PrimaryKey
        var id: String = "",
        var name: String? = "",
        var photo: String? = "",
        var dob: String? = "",
        var blood_type: String? = "",
        var blood_pressure: String = "",
        var email: String? = "",
        var deviceId: String? = "",
        var height: String? = "",
        var weight: String? = "",
        var allergic_medicine: String? = "",
        var phone: String? = "",
        var permanent_address: String = "",
        var address: ArrayList<String> = arrayListOf()
) : Serializable