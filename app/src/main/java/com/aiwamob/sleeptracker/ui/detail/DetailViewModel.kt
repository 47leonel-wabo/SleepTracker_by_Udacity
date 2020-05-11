package com.aiwamob.sleeptracker.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiwamob.sleeptracker.database.SleepDao
import com.aiwamob.sleeptracker.model.ASleep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val sleepDao: SleepDao,
    sleepId: Long
): ViewModel() {

    private val _sleepElt = MutableLiveData<ASleep?>()
    val sleepElt : LiveData<ASleep?>
        get() = _sleepElt

    private val _deletionDone = MutableLiveData<Boolean>()
    val deletionCompleted : LiveData<Boolean>
        get() = _deletionDone

    init {
        _deletionDone.value = false
        viewModelScope.launch {
            _sleepElt.value = getSleep(sleepId)
        }
    }

    private suspend fun getSleep(sleepId: Long): ASleep? {
        return withContext(Dispatchers.IO){
            var sleep = sleepDao.getSleep(sleepId)
            if (sleep == null){
                sleep = null
            }
            sleep
        }
    }

    fun deleteSleep(){
        viewModelScope.launch(Dispatchers.Main) {
            removeSleep(_sleepElt)
        }
    }

    private fun removeSleep(_sleepElt: LiveData<ASleep?>) {
        val sleep : ASleep? = _sleepElt.value
        viewModelScope.launch(Dispatchers.IO) {
            sleepDao.deleteSleep(sleep!!)
            viewModelScope.launch {
                _deletionDone.value = true
            }
        }
    }

    fun sleepDeleteDone(){
        _deletionDone.value = null
        _sleepElt.value = null
    }

}