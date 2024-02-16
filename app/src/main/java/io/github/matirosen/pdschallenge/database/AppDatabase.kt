package io.github.matirosen.pdschallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.matirosen.pdschallenge.dao.LifecycleEventDao
import io.github.matirosen.pdschallenge.entity.LifecycleEventEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [LifecycleEventEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lifecycleEventDao(): LifecycleEventDao

}