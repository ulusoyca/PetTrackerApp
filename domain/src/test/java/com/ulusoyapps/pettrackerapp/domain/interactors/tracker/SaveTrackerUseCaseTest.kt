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
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import com.ulusoyapps.pettrackerapp.domain.repository.TrackerRepository


import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveTrackerUseCaseTest : BaseArchTest() {
    private val trackerRepository: TrackerRepository = mock()
    private val saveTrackerUseCase = SaveTrackerUseCase(trackerRepository)

    private val tracker = Tracker(id = 0)

    @Test
    fun `should save pet`() = runBlocking {
        val expected = Ok(Unit)
        whenever(trackerRepository.saveTracker(tracker)).thenReturn(expected)
        Truth.assertThat(saveTrackerUseCase(tracker)).isEqualTo(expected)
    }

    @Test
    fun `should fail saving pet`() = runBlocking {
        val expected = Err(TrackerMessage())
        whenever(trackerRepository.saveTracker(tracker)).thenReturn(expected)
        Truth.assertThat(saveTrackerUseCase(tracker)).isEqualTo(expected)
    }
}