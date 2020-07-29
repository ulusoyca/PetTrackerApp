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

package com.ulusoyapps.pettrackerapp.domain.interactors.petandtracker

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever

import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.location.Latitude
import com.ulusoyapps.pettrackerapp.domain.entities.location.Longitude
import com.ulusoyapps.pettrackerapp.domain.entities.messages.MockError
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Gender
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.PetAndTracker
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import com.ulusoyapps.pettrackerapp.domain.repository.PetAndTrackerRepository
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetPetAndTrackerUseCaseTest : BaseArchTest() {

    private val petAndTrackerRepository: PetAndTrackerRepository = mock()
    private val getAndTrackerPetUseCase = GetPetAndTrackerUseCase(petAndTrackerRepository)

    private val flow = flow {
        emit(Err(MockError))
        emit(Ok(PetAndTracker(pet, tracker)))
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

    private val tracker = Tracker(id = 0)

    @Test
    fun `should get pet and tracker with pet name`() = runBlocking {
        whenever(petAndTrackerRepository.getPetAndTracker("Fluffy")).thenReturn(flow)
        getAndTrackerPetUseCase("Fluffy").collectIndexed { index, value ->
            if (index == 0) {
                Truth.assertThat(value).isEqualTo(Err(MockError))
            }
            if (index == 1) {
                value.get()?.let {
                    Truth.assertThat(it.pet).isEqualTo(pet)
                    Truth.assertThat(it.tracker).isEqualTo(tracker)
                }
            }
        }
    }
}