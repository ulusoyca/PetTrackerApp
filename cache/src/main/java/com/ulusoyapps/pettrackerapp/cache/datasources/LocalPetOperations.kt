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
import com.ulusoyapps.pettrackerapp.cache.dao.PetDao
import com.ulusoyapps.pettrackerapp.cache.mapper.PetMapper
import com.ulusoyapps.pettrackerapp.datasource.pet.datasource.local.PetCache
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.messages.PetInsertionWithSameId
import com.ulusoyapps.pettrackerapp.domain.entities.messages.PetNotFound
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalPetOperations
@Inject constructor(
    private val petDao: PetDao,
    private val petMapper: PetMapper
) : PetCache {

    override suspend fun getAllPets(): Flow<Result<List<Pet>, DomainMessage>> = flow {
        emit(Ok(petMapper.mapToDomainEntityList(petDao.getAllPets())))
    }.flowOn(Dispatchers.IO)

    override suspend fun getPet(name: String): Flow<Result<Pet, DomainMessage>> = flow {
        val pet = petDao.getPet(name)
        if (pet == null) {
            emit(Err(PetNotFound()))
        } else {
            emit(Ok(petMapper.mapToDomainEntity(pet)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPetAsOneShot(name: String): Result<Pet, DomainMessage> {
        val pet = petDao.getPet(name)
        return if (pet == null) {
            Err(PetNotFound())
        } else {
            Ok(petMapper.mapToDomainEntity(pet))
        }
    }

    override suspend fun savePet(pet: Pet): Result<Unit, DomainMessage> {
        val rowId = petDao.addPet(petMapper.mapFromDomainEntity(pet))
        return if (rowId == -1L) Err(PetInsertionWithSameId()) else Ok(Unit)

    }

    override suspend fun updatePet(pet: Pet): Result<Unit, DomainMessage> {
        val numberOfRowsUpdated = petDao.update(petMapper.mapFromDomainEntity(pet))
        return if (numberOfRowsUpdated == 0) Err(PetNotFound()) else Ok(Unit)
    }

    override suspend fun removePet(pet: Pet): Result<Unit, DomainMessage> {
        val numberOfRowsDeleted = petDao.delete(petMapper.mapFromDomainEntity(pet))
        return if (numberOfRowsDeleted == 0) Err(PetNotFound()) else Ok(Unit)
    }
}