package com.padcx.mmz.doctor.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.padcx.mmz.doctor.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Myint Myint Zaw on 12/12/2020.
 */
fun ImageView.showCropImage(
    imageUrl: String?
) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.doctor)
        .apply(RequestOptions().circleCrop())
        .into(this)
}

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val ymdFormat = SimpleDateFormat("yyyy / MM / dd ")
    return ymdFormat.format(calendar.time)
}

fun getCurrentDateTime(): String {
    val dateFormat: DateFormat = SimpleDateFormat("hh:mm a")
    return dateFormat.format(Date())
    //return SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())
}

fun getCurrentHourMin(): String {
    return SimpleDateFormat("hh:mm a").format(Date())
}
