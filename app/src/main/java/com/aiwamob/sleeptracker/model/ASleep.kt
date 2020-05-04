package com.aiwamob.sleeptracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class ASleep(@PrimaryKey(autoGenerate = true) var sleepId: Long = 0L,
             val startTime: Long = System.currentTimeMillis(),
             var endTime: Long = startTime,
             var sleepQuality: Int = -1)