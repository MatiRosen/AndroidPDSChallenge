package io.github.matirosen.pdschallenge.repository

import io.github.matirosen.pdschallenge.dao.LifecycleEventDao
import io.github.matirosen.pdschallenge.entity.LifecycleEventEntity
import javax.inject.Inject

class LifecycleEventRepository @Inject constructor(
    private val lifecycleEventDao: LifecycleEventDao
){

    suspend fun getAll() =  lifecycleEventDao.getAll()

    suspend fun insert(event: LifecycleEventEntity) =  lifecycleEventDao.insert(event)

    suspend fun deleteAll() = lifecycleEventDao.deleteAll()

}