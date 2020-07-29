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
import com.github.michaelbull.result.Ok
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.location.Latitude
import com.ulusoyapps.pettrackerapp.domain.entities.location.Longitude
import com.ulusoyapps.pettrackerapp.domain.entities.messages.MockError
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Gender
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet
import com.ulusoyapps.unittesting.BaseArchTest
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PetLocalDataSourceTest : BaseArchTest() {

    private val petCache: PetCache = mock()
    private val localDataSource = PetLocalDataSource(petCache)
    private val petsFlow = flow {
        emit(Err(MockError))
        emit(Ok(listOf(pet)))
    }

    private val petFlow = flow {
        emit(Err(MockError))
        emit(Ok(pet))
    }

    private val pet = Pet(
        id = 0L,
        name = "Fluffy",
        breed = "Scottish",
        gender = Gender.MALE,
        safeZoneCenter = LatLng(Latitude(0f), Longitude(0f)),
        birthday = 0L,
        color = ""
    )
    
    private val latLng = LatLng(0.0, 0.0)

    private val success = Ok(Unit)
    private val failure = Err(MockError)


    @Test
    fun testGetAllPets() = runBlocking {
        whenever(petCache.getAllPets()).thenReturn(petsFlow)
        val actual = localDataSource.getAllPets()
        Truth.assertThat(actual).isEqualTo(petsFlow)
    }

    @Test
    fun testGetPet() = runBlocking {
        whenever(petCache.getPet("Fluffy")).thenReturn(petFlow)
        val actual = localDataSource.getPet("Fluffy")
        Truth.assertThat(actual).isEqualTo(petFlow)
    }

    @Test
    fun testSavePet() = runBlocking {
        whenever(petCache.savePet(pet)).thenReturn(success)
        val actual = localDataSource.savePet(pet)
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun testUpdatePet() = runBlocking {
        whenever(petCache.updatePet(pet)).thenReturn(success)
        val actual = localDataSource.updatePet(pet)
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun testRemovePet() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.removePet(pet)).thenReturn(success)
        val actual = localDataSource.removePet("Fluffy")
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun `RemovePet should fail`() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.removePet(pet)).thenReturn(failure)
        val actual = localDataSource.removePet("Fluffy")
        Truth.assertThat(actual).isEqualTo(failure)
    }

    @Test
    fun testSetPetSafeZoneCenter() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.updatePet(pet.copy(safeZoneCenter = latLng))).thenReturn(success)
        val actual = localDataSource.setPetSafeZoneCenter("Fluffy", latLng)
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun `SetPetSafeZoneCenter should fail`() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.updatePet(pet.copy(safeZoneCenter = latLng))).thenReturn(failure)
        val actual = localDataSource.setPetSafeZoneCenter("Fluffy", latLng)
        Truth.assertThat(actual).isEqualTo(failure)
    }


    @Test
    fun testSetPetSafeZoneRadiusInMeters() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.updatePet(pet.copy(safeZoneRadius = 0.0))).thenReturn(success)
        val actual = localDataSource.setPetSafeZoneRadiusInMeters("Fluffy", 0.0)
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun `SetPetSafeZoneRadiusInMeters should fail`() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.updatePet(pet.copy(safeZoneRadius = 0.0))).thenReturn(failure)
        val actual = localDataSource.setPetSafeZoneRadiusInMeters("Fluffy", 0.0)
        Truth.assertThat(actual).isEqualTo(failure)
    }

    @Test
    fun testSetAlarmMode() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.updatePet(pet.copy(alarmEnabled = true))).thenReturn(success)
        val actual = localDataSource.setAlarmMode("Fluffy", true)
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun `SetAlarmMode should fail`() = runBlocking {
        whenever(petCache.getPetAsOneShot(name = "Fluffy")).thenReturn(Ok(pet))
        whenever(petCache.updatePet(pet.copy(alarmEnabled = true))).thenReturn(failure)
        val actual = localDataSource.setAlarmMode("Fluffy", true)
        Truth.assertThat(actual).isEqualTo(failure)
    }
}