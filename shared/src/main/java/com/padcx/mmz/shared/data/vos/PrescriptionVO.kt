package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padcx.mmz.shared.persistance.typeconverters.RoutineConverter

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "prescription")
@IgnoreExtraProperties
@TypeConverters(RoutineConverter::class)
data class PrescriptionVO(
        @PrimaryKey
        var id: String = "",
        var count: String = "",
        var medicine_name: String = "",
        var price: String = "",
        var chat_id : String ="",
        var routineVO: RoutineVO? = RoutineVO()
)