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

package com.ulusoyapps.pettrackerapp.datasource.pet.datasource.local

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import com.ulusoyapps.pettrackerapp.datasource.pet.datasource.PetDataSource
import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet



import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PetLocalDataSource
@Inject constructor(
    private val petCache: PetCache
) : PetDataSource {

    override suspend fun getAllPets(): Flow<Result<List<Pet>, DomainMessage>> {
        return petCache.getAllPets()
    }

    override suspend fun getPet(name: String): Flow<Result<Pet, DomainMessage>> {
        return petCache.getPet(name)
    }

    override suspend fun savePet(pet: Pet): Result<Unit, DomainMessage> {
        return petCache.savePet(pet)
    }

    override suspend fun updatePet(pet: Pet): Result<Unit, DomainMessage> {
        return petCache.updatePet(pet)
    }

    override suspend fun removePet(name: String): Result<Unit, DomainMessage> {
        return petCache.getPetAsOneShot(name = name).mapBoth(
            { pet -> petCache.removePet(pet) },
            { error -> Err(error) }
        )
    }

    override suspend fun setPetSafeZoneCenter(
        petName: String,
        latLng: LatLng
    ): Result<Unit, DomainMessage> {
        return petCache.getPetAsOneShot(name = petName).mapBoth(
            { pet ->
                val updatedPet = pet.copy(safeZoneCenter = latLng)
                petCache.updatePet(updatedPet)
            },
            { error -> Err(error) }
        )
    }

    override suspend fun setPetSafeZoneRadiusInMeters(
        petName: String,
        radius: Double
    ): Result<Unit, DomainMessage> {
        return petCache.getPetAsOneShot(name = petName).mapBoth(
            { pet ->
                val updatedPet = pet.copy(safeZoneRadius = radius)
                petCache.updatePet(updatedPet)
            },
            { error -> Err(error) }
        )
    }

    override suspend fun setAlarmMode(
        petName: String,
        enabled: Boolean
    ): Result<Unit, DomainMessage> {
        return petCache.getPetAsOneShot(name = petName).mapBoth(
            { pet ->
                val updatedPet = pet.copy(alarmEnabled = enabled)
                petCache.updatePet(updatedPet)
            },
            { error -> Err(error) }
        )
    }
}