package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@IgnoreExtraProperties
class CaseSummaryVO(
    var question: String= "",
    var answer: String = ""
)