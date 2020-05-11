package com.aiwamob.sleeptracker.utilities

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.model.ASleep

@BindingAdapter("sleepDurationString")
fun TextView.setSleepDurationStringFormated(item: ASleep?){
    item?.let {
        text = convertLongToDateString(it.startTime)
    }
}

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityFormated(item: ASleep?){
    item?.let {
        text = convertNumericQualityToString(it.sleepQuality)
    }
}

@BindingAdapter("sleepImage")
fun ImageView.setImage(item: ASleep?){
    item?.let {
        setImageResource(when(it.sleepQuality){
            0 -> R.drawable.ic_very_bad_sleep
            1 -> R.drawable.ic_poor_sleep
            2 -> R.drawable.ic_soso_sleep
            3 -> R.drawable.ic_pretty_good_sleep
            4 -> R.drawable.ic_excellent_sleep
            else -> R.drawable.ic_sleeping
        })
    }
}