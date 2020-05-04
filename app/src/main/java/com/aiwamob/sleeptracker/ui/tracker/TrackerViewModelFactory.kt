package com.aiwamob.sleeptracker.ui.tracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aiwamob.sleeptracker.database.SleepDao

class TrackerViewModelFactory(private val dao: SleepDao,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackerViewModel::class.java)){
            return TrackerViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}