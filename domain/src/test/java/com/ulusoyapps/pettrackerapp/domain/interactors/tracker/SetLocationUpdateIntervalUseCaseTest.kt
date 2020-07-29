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

package com.ulusoyapps.pettrackerapp.domain.interactors.tracker

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerMessage
import com.ulusoyapps.pettrackerapp.domain.repository.TrackerRepository


import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SetLocationUpdateIntervalUseCaseTest: BaseArchTest() {

    private val tackerRepository: TrackerRepository = mock()
    private val setLocationUpdateIntervalUseCase = SetLocationUpdateIntervalUseCase(tackerRepository)

    @Test
    fun `should set safe zone center for pet`() = runBlocking {
        val expected = Ok(Unit)
        whenever(tackerRepository.setLocationUpdatePeriodInMs(0, 1000)).thenReturn(expected)
        Truth.assertThat(setLocationUpdateIntervalUseCase(0, 1000)).isEqualTo(expected)
    }

    @Test
    fun `should fail setting safe zone center`() = runBlocking {
        val expected = Err(TrackerMessage())
        whenever(tackerRepository.setLocationUpdatePeriodInMs(0, 1000)).thenReturn(expected)
        Truth.assertThat(setLocationUpdateIntervalUseCase(0, 1000)).isEqualTo(expected)
    }

}