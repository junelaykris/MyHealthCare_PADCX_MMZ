package com.padcx.mmz.shared.network

import com.padcx.mmz.shared.utils.API_KEY
import io.reactivex.Observable
import com.padcx.mmz.shared.network.responses.NotiResponse
import com.padcx.mmz.shared.network.responses.NotificationVO
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:$API_KEY"
    )
    @POST("fcm/send")
    fun sendFcm(@Body notificationVO: NotificationVO) : Observable<NotiResponse>


}