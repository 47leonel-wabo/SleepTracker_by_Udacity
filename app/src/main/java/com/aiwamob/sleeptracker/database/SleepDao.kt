package com.aiwamob.sleeptracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aiwamob.sleeptracker.model.ASleep

@Dao
interface SleepDao {
    @Insert
    fun addSleep(sleep: ASleep)

    @Update
    fun updateSleep(sleep: ASleep)

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY sleepId DESC")
    fun getAllSleeps(): LiveData<List<ASleep>>

    @Query("SELECT * FROM daily_sleep_quality_table WHERE sleepId =(:id)")
    fun getSleep(id: Long): ASleep?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun removeAllSleeps()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY sleepId DESC LIMIT 1")
    fun getLatestSleep(): ASleep?

    @Delete
    fun deleteSleep(sleep: ASleep)
}