package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "general_question_template")
@IgnoreExtraProperties
class GeneralQuestionTemplateVO(
    @PrimaryKey
    var id: String= "",
    var type: String ?= "",
    var question:  String ? = ""
)
