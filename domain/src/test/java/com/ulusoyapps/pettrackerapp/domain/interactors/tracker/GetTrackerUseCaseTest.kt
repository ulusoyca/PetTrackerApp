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
import com.github.michaelbull.result.get
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever



import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import com.ulusoyapps.pettrackerapp.domain.interactors.tracker.GetTrackerUseCase
import com.ulusoyapps.pettrackerapp.domain.repository.TrackerRepository
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTrackerUseCaseTest : BaseArchTest() {

    private val tackerRepository: TrackerRepository = mock()
    private val getTrackerUseCase = GetTrackerUseCase(tackerRepository)

    private val trackerMessage = TrackerMessage()

    private val flow = flow {
        emit(Err(trackerMessage))
        emit(Ok(tracker))
    }

    private val tracker = Tracker(id = 0)

    @Test
    fun `should get tracker with trackerId`() = runBlocking {
        whenever(tackerRepository.getTracker(0)).thenReturn(flow)
        getTrackerUseCase(0).collectIndexed { index, value ->
            if (index == 0) {
                Truth.assertThat(value).isEqualTo(Err(trackerMessage))
            }
            if (index == 1) {
                Truth.assertThat(value.get()).isEqualTo(tracker)
            }
        }
    }
}