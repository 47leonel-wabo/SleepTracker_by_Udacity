package com.aiwamob.sleeptracker.utilities

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.core.text.HtmlCompat
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.model.ASleep
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder
import java.text.SimpleDateFormat

fun makeSnackBar(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(time: Long): String = SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
    .format(time).toString()

fun convertNumericQualityToString(quality: Int, resources: Resources): String{
    var qualityString = "OK"
    when(quality){
        -1 -> qualityString = "--"
        0 -> qualityString = "Very bad"
        1 -> qualityString = "Poor"
        2 -> qualityString = "So-so"
        3 -> qualityString = "Pretty good"
        4 -> qualityString = "Excellent"
    }
    return qualityString
}

fun formatSleep(sleeps: List<ASleep>, resources: Resources): Spanned{

    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        sleeps.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTime)} <br>")
            if (it.startTime != it.endTime){
                append(resources.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTime)} <br>")
                append(resources.getString(R.string.quality))
                append("\t${convertNumericQualityToString(it.sleepQuality, resources)} <br>")
                append(resources.getString(R.string.sleep_hours))
                append("\t ${it.endTime.minus(it.startTime) / 1000 / 60 / 60}:")
                append("\t ${it.endTime.minus(it.startTime) / 1000 / 60 }:")
                append("\t ${it.endTime.minus(it.startTime) / 1000}<br><br>")
            }
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    }else{
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}