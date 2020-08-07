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

package com.ulusoyapps.pettrackerapp.cache.mapper

import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import javax.inject.Inject


class TrackerMapper
@Inject constructor() : EntityMapper<Tracker, CachedTracker> {
    override fun mapFromDomainEntity(type: Tracker): CachedTracker {
        return CachedTracker(
            id = type.id,
            petName = type.petName,
            batteryPercentage = type.batteryPercentage,
            locationUpdatePeriodInMs = type.locationUpdatePeriodInMs
        )
    }

    override fun mapToDomainEntity(type: CachedTracker): Tracker {
        return Tracker(
            id = type.id,
            petName = type.petName,
            batteryPercentage = type.batteryPercentage,
            locationUpdatePeriodInMs = type.locationUpdatePeriodInMs
        )
    }

    override fun mapFromDomainEntityList(type: List<Tracker>): List<CachedTracker> {
        return type.map { mapFromDomainEntity(it) }
    }

    override fun mapToDomainEntityList(type: List<CachedTracker>): List<Tracker> {
        return type.map { mapToDomainEntity(it) }
    }
}