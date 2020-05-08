package com.aiwamob.sleeptracker.ui.quality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aiwamob.sleeptracker.database.SleepDao

class QualityViewModelFactory(private val sleepID: Long, private val dao: SleepDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QualityViewModel::class.java)){
            return QualityViewModel(sleepID, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}