package com.aiwamob.sleeptracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.databinding.SleepItemBinding
import com.aiwamob.sleeptracker.model.ASleep
import com.aiwamob.sleeptracker.utilities.SleepDiffCallback
import com.aiwamob.sleeptracker.utilities.convertNumericQualityToString
import java.text.SimpleDateFormat
import java.util.*

class SleepAdapter: ListAdapter<ASleep, SleepAdapter.SleepViewHolder>(SleepDiffCallback()) {

     /*var data = listOf<ASleep>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/

    class SleepViewHolder private constructor(private val binding: SleepItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(sleep: ASleep){/*
            val sleepTime = (sleep.endTime - sleep.startTime)
            binding.apply {
                sleepLengthTv.text = SimpleDateFormat("EEEE, HH:mm:ss", Locale.ROOT).format(sleepTime)
                sleepQualityTv.text = convertNumericQualityToString(sleep.sleepQuality)
                sleepRatingImageView.setImageResource(when(sleep.sleepQuality){
                    0 -> R.drawable.ic_very_bad_sleep
                    1 -> R.drawable.ic_poor_sleep
                    2 -> R.drawable.ic_soso_sleep
                    3 -> R.drawable.ic_pretty_good_sleep
                    4 -> R.drawable.ic_excellent_sleep
                    else -> R.drawable.ic_sleeping
                })
            }*/

            //*************** USING BIND-ADAPTER INSTEAD ********************************
            binding.apply {
                aSleep = sleep
                executePendingBindings()
            }
        }

        companion object{
            fun from(parent: ViewGroup): SleepViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding: SleepItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.sleep_item, parent, false)

                return SleepViewHolder(itemBinding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        return SleepViewHolder.from(parent)
    }

    //override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}