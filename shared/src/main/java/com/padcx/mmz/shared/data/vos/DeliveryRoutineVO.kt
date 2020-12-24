package com.padcx.mmz.shared.data.vos
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class DeliveryRoutineVO(
        var id: String = "",
        var delivery_date: String? = ""
) {
}