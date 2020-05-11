package com.aiwamob.sleeptracker.utilities

import androidx.recyclerview.widget.DiffUtil
import com.aiwamob.sleeptracker.ui.SleepAdapter

class SleepDiffCallback: DiffUtil.ItemCallback<SleepAdapter.DataItems>() {

    override fun areItemsTheSame(oldItem: SleepAdapter.DataItems, newItem: SleepAdapter.DataItems): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SleepAdapter.DataItems, newItem: SleepAdapter.DataItems): Boolean {
        return oldItem == newItem
    }

}