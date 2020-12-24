package com.padcx.mmz.shared.data.models

import android.content.Context
import com.padcx.mmz.shared.network.ApiService
import com.padcx.mmz.shared.persistance.db.MyHealthCareDB
import com.padcx.mmz.shared.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
abstract class BaseModel {

    protected var mApi: ApiService
    protected lateinit var mTheDB: MyHealthCareDB

    fun initDatabase(context: Context) {
        mTheDB = MyHealthCareDB.getDBInstance(context)
    }

    init {
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(mOkHttpClient)
            .build()

        mApi = retrofit.create(ApiService::class.java)
    }
}