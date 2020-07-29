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

package com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.local

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.TrackerDataSource
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * In this project Remote Data Source is used only for demonstration purposes.
 */
class TrackerLocalDataSource
@Inject constructor(
    private val datasource: TrackerCache
) : TrackerDataSource {

    override suspend fun getTracker(trackerId: Long): Flow<Result<Tracker, DomainMessage>> {
        return datasource.getTracker(trackerId)
    }

    override suspend fun getTrackerOfPet(petName: String): Flow<Result<Tracker, DomainMessage>> {
        return datasource.getTrackerOfPet(petName)
    }

    override suspend fun setLocationUpdatePeriodInMs(
        trackerId: Long,
        intervalInMs: Long
    ): Result<Unit, DomainMessage> {
        return datasource.getTrackerAsOneShot(trackerId).mapBoth(
            {
                val updatedTracker = it.copy(locationUpdatePeriodInMs = intervalInMs)
                datasource.updateTracker(updatedTracker)
            },
            { Err(TrackerMessage()) }
        )
    }


    override suspend fun saveTracker(tracker: Tracker): Result<Unit, DomainMessage> {
        return datasource.saveTracker(tracker)
    }

    override suspend fun removeTracker(trackerId: Long): Result<Unit, DomainMessage> {
        return datasource.getTrackerAsOneShot(trackerId).mapBoth(
            { tracker -> datasource.removeTracker(tracker.id) },
            { Err(TrackerMessage()) }
        )
    }
}