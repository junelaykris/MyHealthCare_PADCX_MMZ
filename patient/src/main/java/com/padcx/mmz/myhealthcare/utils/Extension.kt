package com.padcx.mmz.myhealthcare.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.padcx.mmz.myhealthcare.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Myint Myint Zaw on 12/5/2020.
 */

fun ImageView.showCropImage(
    imageUrl: String?
) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_healthicon)
        .apply(RequestOptions().circleCrop())
        .into(this)
}


fun getCurrentDate():String {
    val calendar = Calendar.getInstance()
    val ymdFormat = SimpleDateFormat("yyyy / MM / dd ")
    return ymdFormat.format(calendar.time)
}

fun getCurrentDateTime() : String{
    return SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())
}

fun getCurrentHourMin() : String{
    return SimpleDateFormat("hh:mm a").format(Date())
}

