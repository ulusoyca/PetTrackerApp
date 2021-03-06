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


import com.ulusoyapps.pettrackerapp.domain.entities.messages.MockError
import com.ulusoyapps.pettrackerapp.domain.repository.PetRepository
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SetAlarmModeUseCaseTest : BaseArchTest() {
    private val petRepository: PetRepository = mock()
    private val setAlarmModeUseCase =
        SetAlarmModeUseCase(
            petRepository
        )

    @Test
    fun `should set the alarm mode to disabled`() = runBlocking {
        val expected = Ok(Unit)
        whenever(petRepository.setAlarmMode("Fluffy", false)).thenReturn(expected)
        Truth.assertThat(setAlarmModeUseCase("Fluffy", false)).isEqualTo(expected)
    }

    @Test
    fun `should fail when setting the alarm mode`() = runBlocking {
        val expected = Err(MockError)
        whenever(petRepository.setAlarmMode("Fluffy", false)).thenReturn(expected)
        Truth.assertThat(setAlarmModeUseCase("Fluffy", false)).isEqualTo(expected)
    }
}