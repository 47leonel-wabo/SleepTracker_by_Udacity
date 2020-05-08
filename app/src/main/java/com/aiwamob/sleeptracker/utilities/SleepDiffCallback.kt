package com.aiwamob.sleeptracker.utilities

import androidx.recyclerview.widget.DiffUtil
import com.aiwamob.sleeptracker.model.ASleep

class SleepDiffCallback: DiffUtil.ItemCallback<ASleep>() {

    override fun areItemsTheSame(oldItem: ASleep, newItem: ASleep): Boolean {
        return oldItem.sleepId == newItem.sleepId
    }

    override fun areContentsTheSame(oldItem: ASleep, newItem: ASleep): Boolean {
        return oldItem == newItem
    }

}