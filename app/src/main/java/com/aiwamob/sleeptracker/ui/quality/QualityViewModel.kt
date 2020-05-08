package com.aiwamob.sleeptracker.ui.quality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiwamob.sleeptracker.database.SleepDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QualityViewModel(private val sleepId: Long, private val dao: SleepDao): ViewModel(){

    private val _sleepQualityToTracker = MutableLiveData<Boolean?>()
    val sleepQualityToTracker: LiveData<Boolean?>
        get() = _sleepQualityToTracker


    fun onSetSleepQuality(quality: Int){
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                val recentSleep = dao.getSleep(sleepId) ?: return@withContext
                recentSleep.sleepQuality = quality
                dao.updateSleep(recentSleep)
            }
            _sleepQualityToTracker.value = true
        }
    }

    fun doneNavigation(){
        _sleepQualityToTracker.value = null
    }
}