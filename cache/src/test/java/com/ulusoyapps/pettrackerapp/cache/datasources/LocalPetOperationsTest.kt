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

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.ulusoyapps.pettrackerapp.cache.dao.PetDao
import com.ulusoyapps.pettrackerapp.cache.entities.CachedLatLng
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet
import com.ulusoyapps.pettrackerapp.cache.mapper.PetMapper
import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.messages.PetInsertionWithSameId
import com.ulusoyapps.pettrackerapp.domain.entities.messages.PetNotFound
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Gender
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LocalPetOperationsTest : BaseArchTest() {
    private val petDao: PetDao = mock()
    private val petMapper: PetMapper = mock()
    private val localPetOperations = LocalPetOperations(petDao, petMapper)

    @Test
    fun `should return empty list if no pets are not found`() = runBlocking {
        whenever(petDao.getAllPets()).thenReturn(emptyList())
        whenever(petMapper.mapToDomainEntityList(emptyList())).thenReturn(emptyList())
        localPetOperations.getAllPets().collect { result ->
            result.onSuccess {
                Truth.assertThat(it).isEmpty()
            }
        }
    }

    @Test
    fun `should return all pets`() = runBlocking {
        val cachedPets = listOf(
            CachedPet(
                name = "Fluffy",
                gender = 1,
                breed = "Breed",
                birthday = 0L,
                color = "",
                safeZoneCenter = CachedLatLng(0f, 0f)
            )
        )
        val pets = listOf(
            Pet(
                name = "Fluffy",
                gender = Gender.MALE,
                breed = "Breed",
                birthday = 0L,
                color = "",
                safeZoneCenter = LatLng(0.0,0.0)
            )
        )
        whenever(petDao.getAllPets()).thenReturn(cachedPets)
        whenever(petMapper.mapToDomainEntityList(cachedPets)).thenReturn(pets)
        localPetOperations.getAllPets().collect { result ->
            result.onSuccess {
                Truth.assertThat(it).isEqualTo(pets)
            }
        }
    }

    @Test
    fun `should get pet with name`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        whenever(petDao.getPet("Fluffy")).thenReturn(cachedPet)
        whenever(petMapper.mapToDomainEntity(cachedPet)).thenReturn(pet)
        localPetOperations.getPet("Fluffy").collect { result ->
            result.onSuccess {
                Truth.assertThat(it).isEqualTo(pet)
            }
        }
    }

    @Test
    fun `should emit error when pet with Id does not exist`() = runBlocking {
        whenever(petDao.getPet("Fluffy")).thenReturn(null)
        verifyZeroInteractions(petMapper)
        localPetOperations.getPet("Fluffy").collect { it ->
            it.onFailure {
                Truth.assertThat(it).isInstanceOf(PetNotFound::class.java)
            }
        }
    }

    @Test
    fun `should emit pet as single with name`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        whenever(petDao.getPet("Fluffy")).thenReturn(cachedPet)
        whenever(petMapper.mapToDomainEntity(cachedPet)).thenReturn(pet)
        var expected: Pet? = null
        localPetOperations.getPetAsOneShot("Fluffy").onSuccess {
            expected = it
        }
        Truth.assertThat(expected).isEqualTo(pet)
    }

    @Test
    fun `should return error when pet with Id does not exist`() = runBlocking {
        whenever(petDao.getPet("Fluffy")).thenReturn(null)
        verifyZeroInteractions(petMapper)
        var expected: PetNotFound? = null
        localPetOperations.getPetAsOneShot("Fluffy").onFailure {
            expected = it as? PetNotFound
        }
        Truth.assertThat(expected).isInstanceOf(PetNotFound::class.java)
    }

    @Test
    fun `should save pet`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        whenever(petMapper.mapFromDomainEntity(pet)).thenReturn(cachedPet)
        whenever(petDao.addPet(cachedPet)).thenReturn(1)
        val expected = localPetOperations.savePet(pet)
        Truth.assertThat(expected).isEqualTo(Ok(Unit))
    }

    @Test
    fun `should not save pet`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        whenever(petMapper.mapFromDomainEntity(pet)).thenReturn(cachedPet)
        whenever(petDao.addPet(cachedPet)).thenReturn(-1)
        var expected: PetInsertionWithSameId? = null
        localPetOperations.savePet(pet).onFailure {
            expected = it as? PetInsertionWithSameId
        }
        Truth.assertThat(expected).isInstanceOf(PetInsertionWithSameId::class.java)
    }

    @Test
    fun `should update pet`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        whenever(petMapper.mapFromDomainEntity(pet)).thenReturn(cachedPet)
        whenever(petDao.update(cachedPet)).thenReturn(1)
        val expected = localPetOperations.updatePet(pet)
        Truth.assertThat(expected).isEqualTo(Ok(Unit))
    }

    @Test
    fun `should return error when pet to be updated does not exist`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        var expected: PetNotFound? = null
        whenever(petMapper.mapFromDomainEntity(pet)).thenReturn(cachedPet)
        whenever(petDao.update(cachedPet)).thenReturn(0)
        localPetOperations.updatePet(pet).onFailure {
            expected = it as? PetNotFound
        }
        Truth.assertThat(expected).isInstanceOf(PetNotFound::class.java)
    }

    @Test
    fun `should delete pet`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        whenever(petMapper.mapFromDomainEntity(pet)).thenReturn(cachedPet)
        whenever(petDao.delete(cachedPet)).thenReturn(1)
        val expected = localPetOperations.removePet(pet)
        Truth.assertThat(expected).isEqualTo(Ok(Unit))
    }

    @Test
    fun `should return error when pet to be deleted does not exist`() = runBlocking {
        val cachedPet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val pet = Pet(
            name = "Fluffy",
            gender = Gender.MALE,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = LatLng(0.0,0.0)
        )
        var expected: PetNotFound? = null
        whenever(petMapper.mapFromDomainEntity(pet)).thenReturn(cachedPet)
        whenever(petDao.delete(cachedPet)).thenReturn(0)
        localPetOperations.removePet(pet).onFailure {
            expected = it as? PetNotFound
        }
        Truth.assertThat(expected).isInstanceOf(PetNotFound::class.java)
    }
}