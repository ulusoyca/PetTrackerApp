/*
 * Copyright 2020 Cagatay Ulusoy (Ulus Oy Apps).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 */

package com.ulusoyapps.pettrackerapp.cache.datasources

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.ulusoyapps.pettrackerapp.cache.dao.TrackerDao
import com.ulusoyapps.pettrackerapp.cache.mapper.TrackerMapper
import com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.local.TrackerCache
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerInsertionWithSameId
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerMessage
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerNotFound
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalTrackerOperations
@Inject constructor(
    private val trackerDao: TrackerDao,
    private val trackerMapper: TrackerMapper
) : TrackerCache {
    override suspend fun getTracker(trackerId: Long): Flow<Result<Tracker, TrackerMessage>> = flow {
        val cachedTracker = trackerDao.getTracker(trackerId)
        if (cachedTracker == null) {
            emit(Err(TrackerNotFound()))
        } else {
            emit(Ok(trackerMapper.mapToDomainEntity(cachedTracker)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTrackerOfPet(petName: String): Flow<Result<Tracker, TrackerMessage>> =
        flow {
            val cachedTracker = trackerDao.getTrackerByPetName(petName)
            if (cachedTracker == null) {
                emit(Err(TrackerNotFound()))
            } else {
                emit(Ok(trackerMapper.mapToDomainEntity(cachedTracker)))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getTrackerAsOneShot(trackerId: Long): Result<Tracker, TrackerMessage> {
        val cachedTracker = trackerDao.getTracker(trackerId)
        return if (cachedTracker == null) {
            Err(TrackerNotFound())
        } else {
            Ok(trackerMapper.mapToDomainEntity(cachedTracker))
        }
    }

    override suspend fun saveTracker(tracker: Tracker): Result<Unit, TrackerMessage> {
        val rowId = trackerDao.addTracker(trackerMapper.mapFromDomainEntity(tracker))
        return if (rowId == -1L) Err(TrackerInsertionWithSameId()) else Ok(Unit)
    }

    override suspend fun updateTracker(tracker: Tracker): Result<Unit, TrackerMessage> {
        val numberOfRowsUpdated = trackerDao.update(trackerMapper.mapFromDomainEntity(tracker))
        return if (numberOfRowsUpdated == 0) Err(TrackerNotFound()) else Ok(Unit)
    }

    override suspend fun deleteTracker(trackerId: Long): Result<Unit, TrackerMessage> {
        val numberOfRowsDeleted = trackerDao.deleteByTrackerId(trackerId)
        return if (numberOfRowsDeleted == 0) Err(TrackerNotFound()) else Ok(Unit)
    }
}