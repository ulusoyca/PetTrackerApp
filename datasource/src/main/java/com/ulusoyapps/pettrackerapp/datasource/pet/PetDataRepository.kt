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

package com.ulusoyapps.pettrackerapp.datasource.pet

import com.github.michaelbull.result.Result
import com.ulusoyapps.pettrackerapp.datasource.pet.datasource.PetDataSource
import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet


import com.ulusoyapps.pettrackerapp.domain.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PetDataRepository
@Inject constructor(
    private val localDataSource: PetDataSource
) : PetRepository {
    override suspend fun getAllPets(): Flow<Result<List<Pet>, DomainMessage>>
            = localDataSource.getAllPets()

    override suspend fun getPet(name: String): Flow<Result<Pet, DomainMessage>>
            = localDataSource.getPet(name)

    override suspend fun savePet(pet: Pet): Result<Unit, DomainMessage>
            = localDataSource.savePet(pet)

    override suspend fun deletePet(name: String): Result<Unit, DomainMessage>
            = localDataSource.removePet(name)

    override suspend fun setPetSafeZoneCenter(petName: String, latLng: LatLng): Result<Unit, DomainMessage>
            = localDataSource.setPetSafeZoneCenter(petName, latLng)

    override suspend fun setPetSafeZoneRadiusInMeters(petName: String, radius: Double): Result<Unit, DomainMessage>
            = localDataSource.setPetSafeZoneRadiusInMeters(petName, radius)

    override suspend fun setAlarmMode(petName: String, enabled: Boolean): Result<Unit, DomainMessage>
            = localDataSource.setAlarmMode(petName, enabled)

    override suspend fun updatePet(pet: Pet): Result<Unit, DomainMessage>
            = localDataSource.updatePet(pet)
}