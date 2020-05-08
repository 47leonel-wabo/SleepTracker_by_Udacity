package com.aiwamob.sleeptracker.ui.tracker

import android.app.Application
import androidx.lifecycle.*
import com.aiwamob.sleeptracker.database.SleepDao
import com.aiwamob.sleeptracker.model.ASleep
import com.aiwamob.sleeptracker.utilities.formatSleep
import kotlinx.coroutines.*

class TrackerViewModel(sleepDao: SleepDao, application: Application) :
    AndroidViewModel(application) {

    init {
        initializeToNight()
    }

    private var toNight = MutableLiveData<ASleep?>()

    private val allSleeps = sleepDao.getAllSleeps()

    private val _navigateToQuality = MutableLiveData<ASleep>()
    val navigateToSleepQuality: LiveData<ASleep>
        get() = _navigateToQuality

    private fun initializeToNight() {
        viewModelScope.launch(Dispatchers.Main) {
            toNight.value = getRecentSleep()
        }
    }

    private val dao = sleepDao

    private suspend fun getRecentSleep(): ASleep? {

        return withContext(Dispatchers.IO) {
            var night = dao.getLatestSleep()
            if (night?.endTime != night?.startTime) {
                night = null
            }
            night
        }
    }


    fun doneNavigation(){
        _navigateToQuality.value = null
    }

    val sleepString = Transformations.map(allSleeps){
        it?.let {
            formatSleep(it, application.resources)
        }
    }

    fun startTrackingSleep(){
        viewModelScope.launch(Dispatchers.Main) {
            val sleep = ASleep()

            addSleep(sleep)

            toNight.value = getRecentSleep()
        }
    }

    private suspend fun addSleep(sleep: ASleep) {
        withContext(Dispatchers.IO){
            dao.addSleep(sleep)
        }
    }

    fun stopTrackingSleep(){
        viewModelScope.launch(Dispatchers.Main){
            val oldSleep = toNight.value ?: return@launch
            oldSleep.endTime = System.currentTimeMillis()

            updateOldSleep(oldSleep)
            _navigateToQuality.value = oldSleep
        }
    }

    private suspend fun updateOldSleep(oldSleep: ASleep) {
        withContext(Dispatchers.IO){
            dao.updateSleep(oldSleep)
        }
    }

    fun clear(){
        viewModelScope.launch(Dispatchers.Main){
            clearData()
            toNight.value = null
        }
    }

    private suspend fun clearData() {
        withContext(Dispatchers.IO){
            dao.removeAllSleeps()
        }
    }

/*    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }*/
}