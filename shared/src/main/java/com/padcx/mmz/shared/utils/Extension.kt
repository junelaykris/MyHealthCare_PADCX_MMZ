package com.padcx.mmz.shared.utils

import android.annotation.SuppressLint
import com.padcx.mmz.shared.data.vos.GeneralQuestionVO
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Myint Myint Zaw on 12/5/2020.
 */
fun MutableMap<String,Any>?.convertToGeneralQuestionVO() : GeneralQuestionVO {
    val questionVO = GeneralQuestionVO()
    questionVO.sq_id = this?.get("id") as String
    questionVO.question = this["question"] as String
    questionVO.type = this["type"]as String
    return questionVO
}

fun getDayAgo(dayAgo:Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -dayAgo)
    return calendar.time
}

@SuppressLint("CheckResult")
fun Completable.dbOperationResult(onSuccess:(String)->Unit, onFailure:(String)->Unit){
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe{
            doOnComplete {
                onSuccess("Database CRUD Success...")
            }
            doOnError {
                onFailure("${it.message}")
            }
        }
}

fun getCurrentDate():String {
    val calendar = Calendar.getInstance()
    val ymdFormat = SimpleDateFormat("yyyy / MM / dd ")
    return ymdFormat.format(calendar.time)
}


