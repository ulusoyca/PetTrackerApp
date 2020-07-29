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

class DeleteTrackerUseCaseTest : BaseArchTest() {

    private val trackerRepository: TrackerRepository = mock()
    private val removePetUseCase = DeleteTrackerUseCase(trackerRepository)

    @Test
    fun `should remove pet with a name`() = runBlocking {
        val expected = Ok(Unit)
        whenever(trackerRepository.deleteTracker(0)).thenReturn(expected)
        Truth.assertThat(removePetUseCase(0)).isEqualTo(expected)
    }

    @Test
    fun `should fail removing a pet`() = runBlocking {
        val expected = Err(TrackerMessage())
        whenever(trackerRepository.deleteTracker(0)).thenReturn(expected)
        Truth.assertThat(removePetUseCase(0)).isEqualTo(expected)
    }
}