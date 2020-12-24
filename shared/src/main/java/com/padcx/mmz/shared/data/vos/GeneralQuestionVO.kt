package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "general_question")
@IgnoreExtraProperties
data class GeneralQuestionVO(
        @PrimaryKey
        var sq_id: String = "",
        var question: String = "",
        var type: String = ""
)