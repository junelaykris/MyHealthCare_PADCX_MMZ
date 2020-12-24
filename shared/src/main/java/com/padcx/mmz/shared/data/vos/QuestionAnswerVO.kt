package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@Entity(tableName = "question_answer")
@IgnoreExtraProperties
data class QuestionAnswerVO(
    var id : String ="",
    var question: String= "",
    var answer: String = ""
):Serializable