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

package com.ulusoyapps.pettrackerapp.domain.interactors.pet

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
import com.ulusoyapps.pettrackerapp.domain.repository.PetRepository
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdatePetUseCaseTest : BaseArchTest() {

    private val petRepository: PetRepository = mock()
    private val updatePetUseCase = UpdatePetUseCase(petRepository
    )

    private val pet = Pet(
        name = "Fluffy",
        breed = "Scottish",
        gender = Gender.MALE,
        safeZoneCenter = LatLng(Latitude(0f), Longitude(0f)),
        birthday = 0L,
        color = ""
    )

    @Test
    fun `should save pet`() = runBlocking {
        val expected = Ok(Unit)
        whenever(petRepository.updatePet(pet)).thenReturn(expected)
        Truth.assertThat(updatePetUseCase(pet)).isEqualTo(expected)
    }

    @Test
    fun `should fail saving pet`() = runBlocking {
        val expected = Err(MockError)
        whenever(petRepository.updatePet(pet)).thenReturn(expected)
        Truth.assertThat(updatePetUseCase(pet)).isEqualTo(expected)
    }

}