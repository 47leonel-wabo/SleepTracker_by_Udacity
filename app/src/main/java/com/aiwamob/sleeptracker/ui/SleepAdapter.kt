package com.aiwamob.sleeptracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aiwamob.sleeptracker.R
import com.aiwamob.sleeptracker.databinding.SleepItemBinding
import com.aiwamob.sleeptracker.databinding.TextViewBindingBinding
import com.aiwamob.sleeptracker.model.ASleep
import com.aiwamob.sleeptracker.utilities.SleepDiffCallback
import kotlinx.coroutines.*

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class SleepAdapter(private val clickedItemListener: SleepClickListener): ListAdapter<SleepAdapter.DataItems,RecyclerView.ViewHolder >(SleepDiffCallback()) {

     /*var data = listOf<ASleep>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class TextViewHolder private constructor(txtBinding: TextViewBindingBinding): RecyclerView.ViewHolder(txtBinding.root){
        companion object{
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val headBinding: TextViewBindingBinding = DataBindingUtil.inflate(layoutInflater, R.layout.text_view_binding, parent, false)

                return TextViewHolder(headBinding)
            }
        }
    }

    class SleepViewHolder private constructor(private val binding: SleepItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(sleep: ASleep, itemClickListener: SleepClickListener){/*
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
                clickListener = itemClickListener
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

    fun addHeaderAndSubmitList(list: List<ASleep>?){
        adapterScope.launch {
            val item = when(list){
                null -> listOf(DataItems.Header)
                else -> listOf(DataItems.Header) + list.map { DataItems.SleepItem(it) }
            }
            withContext(Dispatchers.Main){
                submitList(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> SleepViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when(getItem(position)){
            is DataItems.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItems.SleepItem -> ITEM_VIEW_TYPE_ITEM
            else -> super.getItemViewType(position)
        }

    }

    //override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder){
            is SleepViewHolder -> {
                val sleepItem = getItem(position) as DataItems.SleepItem
                holder.bind(sleepItem.sleep, clickedItemListener)
            }
        }
    }

    class SleepClickListener(val clickedItem: (sleepID: Long) -> Unit){

        fun onSleepClicked(sleep: ASleep) = clickedItem(sleep.sleepId)

    }
    //************* HEADER IMPL *****************************
    sealed class DataItems{

        data class SleepItem(val sleep : ASleep) : DataItems() {
            override val id = sleep.sleepId
        }

        object Header: DataItems(){
            override val id: Long = Long.MIN_VALUE
        }

        abstract val id: Long
    }
}