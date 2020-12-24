package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@IgnoreExtraProperties
data class SenderVO (
        var photo: String ? = "",
        var name: String ? = "",
        var arrived_time : String ? = ""
)