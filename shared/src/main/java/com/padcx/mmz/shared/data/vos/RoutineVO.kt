package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@IgnoreExtraProperties
data class RoutineVO(
        var id: String = "",
        var amount: String? = "",
        var days: String? = "",
        var comment: String? = "",
        var repeat: String? = "",
        var quantity: String? = "",
        var times: String? = "",
)