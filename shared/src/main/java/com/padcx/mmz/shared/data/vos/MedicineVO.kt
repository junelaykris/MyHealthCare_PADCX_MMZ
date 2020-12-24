package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "medicine")
@IgnoreExtraProperties
data class MedicineVO(
        @PrimaryKey
        var id: String,
        var name: String? = "",
        var price: Int? = 0,
        var isSelected: Boolean? = false
)