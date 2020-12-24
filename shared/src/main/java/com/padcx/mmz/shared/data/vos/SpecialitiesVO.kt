package com.padcx.mmz.shared.data.vos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "specialities")
@IgnoreExtraProperties
data class SpecialitiesVO(
        @PrimaryKey
        var id: String= "",
        var name: String = "",
        var photo: String = "",
)