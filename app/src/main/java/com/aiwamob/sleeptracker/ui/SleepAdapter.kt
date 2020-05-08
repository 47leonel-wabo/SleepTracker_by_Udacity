package com.aiwamob.sleeptracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.model.ASleep
import com.aiwamob.sleeptracker.utilities.convertNumericQualityToString
import java.text.SimpleDateFormat
import java.util.*

class SleepAdapter: RecyclerView.Adapter<SleepAdapter.SleepViewHolder>() {

     var data = listOf<ASleep>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SleepViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val sleepImageQuality: ImageView = view.findViewById(R.id.sleepRatingImageView)
        private val sleepLength: TextView = view.findViewById(R.id.sleepLength_tv)
        private val sleepQuality: TextView = view.findViewById(R.id.sleepQuality_tv)


        fun bind(sleep: ASleep){
            val sleepTime = (sleep.endTime - sleep.startTime)
            sleepLength.text = SimpleDateFormat("EEEE, HH:mm:ss", Locale.ROOT).format(sleepTime)
            sleepQuality.text = convertNumericQualityToString(sleep.sleepQuality)
            sleepImageQuality.setImageResource(when(sleep.sleepQuality){
                0 -> R.drawable.ic_very_bad_sleep
                1 -> R.drawable.ic_poor_sleep
                2 -> R.drawable.ic_soso_sleep
                3 -> R.drawable.ic_pretty_good_sleep
                4 -> R.drawable.ic_excellent_sleep
                else -> R.drawable.ic_sleeping
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.sleep_item, parent, false)

        return SleepViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}