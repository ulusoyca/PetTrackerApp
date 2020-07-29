/*
 * Copyright 2020 Cagatay Ulusoy (Ulus Oy Apps). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 *  See the License for the specific language governing permissions and limitations under the License.
 */

package com.ulusoyapps.pettrackerapp.datasource.tracker

import com.github.michaelbull.result.Result
import com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.TrackerDataSource
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker

import com.ulusoyapps.pettrackerapp.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackerDataRepository
@Inject constructor(
    private val localDatasource: TrackerDataSource
) : TrackerRepository {

    override suspend fun getTracker(
        trackerId: Long
    ): Flow<Result<Tracker, DomainMessage>> {
        return localDatasource.getTracker(trackerId)
    }

    override suspend fun getTrackerOfPet(petName: String): Flow<Result<Tracker, DomainMessage>> {
        return localDatasource.getTrackerOfPet(petName)
    }

    override suspend fun saveTracker(tracker: Tracker): Result<Unit, DomainMessage> {
        return localDatasource.saveTracker(tracker)
    }

    override suspend fun removeTracker(trackerId: Long): Result<Unit, DomainMessage> {
        return localDatasource.removeTracker(trackerId)
    }

    override suspend fun setLocationUpdatePeriodInMs(
        trackerId: Long,
        intervalInMs: Long
    ): Result<Unit, DomainMessage> {
        return localDatasource.setLocationUpdatePeriodInMs(trackerId, intervalInMs)
    }
}