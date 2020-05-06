package com.aiwamob.sleeptracker.ui.tracker

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.aiwamob.sleeptracker.database.SleepDao
import com.aiwamob.sleeptracker.model.ASleep
import com.aiwamob.sleeptracker.utilities.formatSleep
import kotlinx.coroutines.*

class TrackerViewModel(sleepDao: SleepDao, application: Application) :
    AndroidViewModel(application) {

    init {
        initializeToNight()
    }

    private fun initializeToNight() {
        viewModelScope.launch(Dispatchers.Main) {
            toNight.value = getLastToNight()
        }
    }

    private val dao = sleepDao
    private suspend fun getLastToNight(): ASleep? {
        return withContext(Dispatchers.IO) {
            var night = dao.getLatestSleep()
            night?.let {
                if (night?.endTime != night?.startTime) {
                    night = null
                }
            }
            night
        }
    }

    //private var viewModelJob = Job()

    //private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var toNight = MutableLiveData<ASleep?>()

    private val allSleeps = sleepDao.getAllSleeps()

    val sleepString = Transformations.map(allSleeps){
        it?.let {
            formatSleep(it, application.resources)
        }
    }

    fun startTrackingSleep(){
        viewModelScope.launch(Dispatchers.Main) {
            val sleep = ASleep()

            addSleep(sleep)

            toNight.value = getLastToNight()
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