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

import com.ulusoyapps.pettrackerapp.cache.entities.relationship.CachedPetAndCachedTracker
import com.ulusoyapps.pettrackerapp.cache.mapper.EntityMapper
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.PetAndTracker
import javax.inject.Inject


class PetAndTrackerMapper
@Inject constructor(
    private val tackerMapper: TrackerMapper,
    private val petMapper: PetMapper
) : EntityMapper<PetAndTracker, CachedPetAndCachedTracker> {


    override fun mapFromDomainEntity(type: PetAndTracker): CachedPetAndCachedTracker {
        return CachedPetAndCachedTracker(
            tracker = type.tracker?.let { tackerMapper.mapFromDomainEntity(it) },
            pet = petMapper.mapFromDomainEntity(type.pet)
        )
    }

    override fun mapToDomainEntity(type: CachedPetAndCachedTracker): PetAndTracker {
        return PetAndTracker(
            tracker = type.tracker?.let {tackerMapper.mapToDomainEntity(it) },
            pet = petMapper.mapToDomainEntity(type.pet)
        )
    }

    override fun mapFromDomainEntityList(type: List<PetAndTracker>): List<CachedPetAndCachedTracker> {
        return type.map { mapFromDomainEntity(it) }
    }

    override fun mapToDomainEntityList(type: List<CachedPetAndCachedTracker>): List<PetAndTracker> {
        return type.map { mapToDomainEntity(it) }
    }
}