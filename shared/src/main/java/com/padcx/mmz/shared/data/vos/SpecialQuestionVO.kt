package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "special_question")
@IgnoreExtraProperties
data class SpecialQuestionVO(
        @PrimaryKey
        var id: String = "",
        var question: String = ""
)