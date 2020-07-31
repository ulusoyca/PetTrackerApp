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
import com.ulusoyapps.pettrackerapp.cache.dao.PetAndTrackerDao
import com.ulusoyapps.pettrackerapp.cache.mapper.PetAndTrackerMapper
import com.ulusoyapps.pettrackerapp.datasource.petandtracker.datasource.local.PetAndTrackerCache
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.messages.PetNotFound
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.PetAndTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalPetAndTrackerOperations
@Inject constructor(
    private val petAndTrackerDao: PetAndTrackerDao,
    private val petAndTrackerMapper: PetAndTrackerMapper
) : PetAndTrackerCache {
    override suspend fun getPetAndTracker(petName: String): Flow<Result<PetAndTracker, DomainMessage>> = flow {
        val pet = petAndTrackerDao.getCachedPetAndCachedTracker(petName)
        if (pet == null) {
            emit(Err(PetNotFound()))
        } else {
            emit(Ok(petAndTrackerMapper.mapToDomainEntity(pet)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllPetAndTrackers(): Flow<Result<List<PetAndTracker>, DomainMessage>> = flow {
        val pets = petAndTrackerDao.getAllCachedPetAndCachedTrackers()
        emit(Ok(petAndTrackerMapper.mapToDomainEntityList(pets)))
    }.flowOn(Dispatchers.IO)
}