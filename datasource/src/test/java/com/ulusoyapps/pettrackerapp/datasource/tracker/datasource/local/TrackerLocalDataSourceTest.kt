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

package com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.local

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ulusoyapps.pettrackerapp.domain.entities.messages.MockError
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TrackerLocalDataSourceTest : BaseArchTest() {

    private val trackerCache: TrackerCache = mock()
    private val localDataSource = TrackerLocalDataSource(trackerCache)

    private val tracker = Tracker(id = 0)

    private val flow = flow {
        emit(Err(MockError))
        emit(Ok(tracker))
    }

    private val success = Ok(Unit)
    private val failure = Err(MockError)

    @Test
    fun testGetTracker() = runBlocking {
        whenever(trackerCache.getTracker(0L)).thenReturn(flow)
        val actual = localDataSource.getTracker(0L)
        Truth.assertThat(actual).isEqualTo(flow)
    }

    @Test
    fun testGetTrackerOfPet() = runBlocking {
        whenever(trackerCache.getTrackerOfPet("Fluffy")).thenReturn(flow)
        val actual = localDataSource.getTrackerOfPet("Fluffy")
        Truth.assertThat(actual).isEqualTo(flow)
    }

    @Test
    fun testSetLocationUpdatePeriodInMs() = runBlocking {
        whenever(trackerCache.getTrackerAsOneShot(0L)).thenReturn(Ok(tracker))
        whenever(trackerCache.updateTracker(tracker.copy(locationUpdatePeriodInMs = 1L))).thenReturn(success)
        val actual = localDataSource.setLocationUpdatePeriodInMs(0L, 1L)
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun `testSetLocationUpdatePeriodInMs should fail`() = runBlocking {
        whenever(trackerCache.getTrackerAsOneShot(0L)).thenReturn(Ok(tracker))
        whenever(trackerCache.updateTracker(tracker.copy(locationUpdatePeriodInMs = 1L))).thenReturn(failure)
        val actual = localDataSource.setLocationUpdatePeriodInMs(0L, 1L)
        Truth.assertThat(actual).isEqualTo(failure)
    }

    @Test
    fun testSaveTracker() = runBlocking {
        whenever(trackerCache.saveTracker(tracker)).thenReturn(success)
        val actual = localDataSource.saveTracker(tracker)
        Truth.assertThat(actual).isEqualTo(success)
    }

    @Test
    fun testRemoveTracker() = runBlocking {
        whenever(trackerCache.getTrackerAsOneShot(0L)).thenReturn(Ok(tracker))
        whenever(trackerCache.removeTracker(0L)).thenReturn(success)
        val actual = localDataSource.removeTracker(0L)
        Truth.assertThat(actual).isEqualTo(success)
    }
}