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
import com.ulusoyapps.pettrackerapp.domain.entities.messages.MockError
import com.ulusoyapps.pettrackerapp.domain.repository.PetRepository
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemovePetUseCaseTest : BaseArchTest() {

    private val petRepository: PetRepository = mock()
    private val removePetUseCase = DeletePetUseCase(petRepository)

    @Test
    fun `should remove pet with a name`() = runBlocking {
        val expected = Ok(Unit)
        whenever(petRepository.deletePet("Fluffy")).thenReturn(expected)
        Truth.assertThat(removePetUseCase("Fluffy")).isEqualTo(expected)
    }

    @Test
    fun `should fail removing a pet`() = runBlocking {
        val expected = Err(MockError)
        whenever(petRepository.deletePet("Fluffy")).thenReturn(expected)
        Truth.assertThat(removePetUseCase("Fluffy")).isEqualTo(expected)
    }
}