package io.github.matirosen.pdschallenge.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lifecycle_events")
data class LifecycleEventEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "event_name")
    val eventName: String,
    @ColumnInfo(name = "event_timestamp")
    val eventTimestamp: Long,
){
}