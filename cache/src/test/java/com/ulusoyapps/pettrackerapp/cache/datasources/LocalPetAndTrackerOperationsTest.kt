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

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.ulusoyapps.pettrackerapp.cache.dao.PetAndTrackerDao
import com.ulusoyapps.pettrackerapp.cache.datasources.LocalPetAndTrackerOperations
import com.ulusoyapps.pettrackerapp.cache.entities.CachedLatLng
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet
import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker
import com.ulusoyapps.pettrackerapp.cache.entities.relationship.CachedPetAndCachedTracker
import com.ulusoyapps.pettrackerapp.cache.mapper.PetAndTrackerMapper
import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.messages.PetNotFound
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Gender
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.PetAndTracker
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LocalPetAndTrackerOperationsTest : BaseArchTest() {
    private val petAndTrackerDao: PetAndTrackerDao = mock()
    private val petAndTrackerMapper: PetAndTrackerMapper = mock()
    private val localPetAndTrackerOperations = LocalPetAndTrackerOperations(
        petAndTrackerDao, petAndTrackerMapper
    )

    @Test
    fun `should get pet and tracker from pet Id`() = runBlocking {
        val pet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val tracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val cachedPetAndTracker = CachedPetAndCachedTracker(
            pet = pet,
            tracker = tracker
        )
        val domainPetAndTracker = PetAndTracker(
            pet = Pet(
                name = "Fluffy",
                gender = Gender.MALE,
                breed = "Breed",
                birthday = 0L,
                color = "",
                safeZoneCenter = LatLng(0.0,0.0)
            ),
            tracker = Tracker(
                id = 0L,
                petName = "Fluffy"
            )
        )
        whenever(petAndTrackerDao.getCachedPetAndCachedTracker("Fluffy")).thenReturn(cachedPetAndTracker)
        whenever(petAndTrackerMapper.mapToDomainEntity(cachedPetAndTracker)).thenReturn(domainPetAndTracker)
        localPetAndTrackerOperations.getPetAndTracker("Fluffy").collect { result ->
            result.onSuccess { it ->
                Truth.assertThat(it).isEqualTo(domainPetAndTracker)
            }
        }
    }

    @Test
    fun `should return error when pet with Id does not exist`() = runBlocking {
        whenever(petAndTrackerDao.getCachedPetAndCachedTracker("Fluffy")).thenReturn(null)
        verifyZeroInteractions(petAndTrackerMapper)
        localPetAndTrackerOperations.getPetAndTracker("Fluffy").collect { it ->
            it.onFailure {
                Truth.assertThat(it).isInstanceOf(PetNotFound::class.java)
            }
        }
    }
}