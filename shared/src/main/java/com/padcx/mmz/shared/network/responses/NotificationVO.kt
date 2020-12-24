package com.padcx.mmz.shared.network.responses

import com.google.gson.annotations.SerializedName


data class NotificationVO(
        @SerializedName("to")
        var to:String? = "",
        @SerializedName("data")
        var data : DataVO? = DataVO()
)
