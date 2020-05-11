package com.aiwamob.sleeptracker.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aiwamob.sleeptracker.database.SleepDao

class DetailViewModelFactory(private val sleepDao: SleepDao, private val sleepId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(sleepDao, sleepId) as T
        }
        throw IllegalArgumentException("Class unknown...")
    }
}