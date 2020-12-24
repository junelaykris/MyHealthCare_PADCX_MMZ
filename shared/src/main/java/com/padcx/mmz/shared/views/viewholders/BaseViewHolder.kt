package com.padcx.mmz.shared.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Myint Myint Zaw on 9/5/2020.
 */
abstract class BaseViewHolder<T>(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    var mData : T? = null

    abstract fun bindData(data : T)
}