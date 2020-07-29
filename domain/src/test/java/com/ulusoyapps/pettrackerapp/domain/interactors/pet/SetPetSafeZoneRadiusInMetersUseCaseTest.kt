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


import com.ulusoyapps.pettrackerapp.domain.repository.PetRepository
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SetPetSafeZoneRadiusInMetersUseCaseTest : BaseArchTest() {
    private val petRepository: PetRepository = mock()
    private val setPetSafeZoneRadiusInMetersUseCase =
        SetPetSafeZoneRadiusInMetersUseCase(
            petRepository
        )

    private val center = LatLng(
        Latitude(0f),
        Longitude(0f)
    )

    @Test
    fun `should set safe zone radius`() = runBlocking {
        val expected = Ok(Unit)
        whenever(petRepository.setPetSafeZoneRadiusInMeters("Fluffy", 1000.0)).thenReturn(expected)
        Truth.assertThat(setPetSafeZoneRadiusInMetersUseCase("Fluffy", 1000.0)).isEqualTo(expected)
    }

    @Test
    fun `should fail setting safe zone radius`() = runBlocking {
        val expected = Err(MockError)
        whenever(petRepository.setPetSafeZoneRadiusInMeters("Fluffy", 1000.0)).thenReturn(expected)
        Truth.assertThat(setPetSafeZoneRadiusInMetersUseCase("Fluffy", 1000.0)).isEqualTo(expected)
    }
}