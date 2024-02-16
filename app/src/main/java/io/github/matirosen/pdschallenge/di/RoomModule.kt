package io.github.matirosen.pdschallenge.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.matirosen.pdschallenge.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val LIFECYCLE_EVENT_DATABASE_NAME = "lifecycle_event_database"

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            LIFECYCLE_EVENT_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideLifecycleEventDao(appDatabase: AppDatabase) = appDatabase.lifecycleEventDao()
}