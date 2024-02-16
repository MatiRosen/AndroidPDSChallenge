package io.github.matirosen.pdschallenge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.matirosen.pdschallenge.entity.LifecycleEventEntity

@Dao
interface LifecycleEventDao {

    @Query("SELECT * FROM lifecycle_events")
    suspend fun getAll(): List<LifecycleEventEntity>

    @Insert
    suspend fun insert(event: LifecycleEventEntity)

    @Query("DELETE FROM lifecycle_events")
    suspend fun deleteAll()

}