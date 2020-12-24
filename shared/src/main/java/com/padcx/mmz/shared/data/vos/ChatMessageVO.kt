package com.padcx.mmz.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.IgnoreExtraProperties
import com.padcx.mmz.shared.persistance.typeconverters.SenderTypeConverter

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Entity(tableName = "chat_message")
@IgnoreExtraProperties
@TypeConverters(SenderTypeConverter::class)
data class ChatMessageVO(
        @PrimaryKey
        var id: String = "",
        var messageText: String = "",
        var messageImage: String? = "",
        var sendAt: String? = "",
        var sendBy: SenderVO? = SenderVO(),
        var type: String? = ""
)