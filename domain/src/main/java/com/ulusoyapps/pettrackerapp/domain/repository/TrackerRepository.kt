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

package com.ulusoyapps.pettrackerapp.domain.repository
import com.github.michaelbull.result.Result
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import kotlinx.coroutines.flow.Flow

interface TrackerRepository {
    suspend fun getTracker(trackerId: Long): Flow<Result<Tracker, DomainMessage>>
    suspend fun getTrackerOfPet(petName: String): Flow<Result<Tracker, DomainMessage>>
    suspend fun saveTracker(tracker: Tracker): Result<Unit, DomainMessage>
    suspend fun deleteTracker(trackerId: Long): Result<Unit, DomainMessage>
    suspend fun setLocationUpdatePeriodInMs(trackerId: Long, intervalInMs: Long): Result<Unit, DomainMessage>
}