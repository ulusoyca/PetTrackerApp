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

package com.ulusoyapps.pettrackerapp.cache.datasources

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.ulusoyapps.pettrackerapp.cache.dao.TrackerDao
import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker
import com.ulusoyapps.pettrackerapp.cache.mapper.TrackerMapper
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerInsertionWithSameId
import com.ulusoyapps.pettrackerapp.domain.entities.messages.TrackerNotFound
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Tracker
import com.ulusoyapps.unittesting.BaseArchTest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LocalTrackerOperationsTest : BaseArchTest() {
    private val trackerDao: TrackerDao = mock()
    private val trackerMapper: TrackerMapper = mock()
    private val localTrackerOperations = LocalTrackerOperations(trackerDao, trackerMapper)

    @Test
    fun `should get tracker with id`() = runBlocking {
        val cachedTracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val tracker = Tracker(
            id = 0L,
            petName = "Fluffy"
        )
        whenever(trackerDao.getTracker(0)).thenReturn(cachedTracker)
        whenever(trackerMapper.mapToDomainEntity(cachedTracker)).thenReturn(tracker)
        localTrackerOperations.getTracker(0).collect { result ->
            result.onSuccess {
                Truth.assertThat(it).isEqualTo(tracker)
            }
        }
    }

    @Test
    fun `should emit error when tracker with Id does not exist`() = runBlocking {
        whenever(trackerDao.getTracker(0)).thenReturn(null)
        verifyZeroInteractions(trackerMapper)
        localTrackerOperations.getTracker(0).collect { it ->
            it.onFailure {
                Truth.assertThat(it).isInstanceOf(TrackerNotFound::class.java)
            }
        }
    }

    @Test
    fun `should get tracker with pet with name`() = runBlocking {
        whenever(trackerDao.getTrackerByPetName("Flu")).thenReturn(null)
        verifyZeroInteractions(trackerMapper)
        localTrackerOperations.getTrackerOfPet("Flu").collect { it ->
            it.onFailure {
                Truth.assertThat(it).isInstanceOf(TrackerNotFound::class.java)
            }
        }
    }

    @Test
    fun `should return error when tracker with pet name does not exist`() = runBlocking {
        whenever(trackerDao.getTrackerByPetName("Fluffy")).thenReturn(null)
        verifyZeroInteractions(trackerMapper)
        localTrackerOperations.getTrackerOfPet("Fluffy").collect { result ->
            result.onFailure {
                Truth.assertThat(it).isInstanceOf(TrackerNotFound::class.java)
            }
        }
    }

    @Test
    fun `should return tracker as single with tracker id`() = runBlocking {
        val cachedTracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val tracker = Tracker(
            id = 0L,
            petName = "Fluffy"
        )
        whenever(trackerDao.getTracker(0L)).thenReturn(cachedTracker)
        whenever(trackerMapper.mapToDomainEntity(cachedTracker)).thenReturn(tracker)
        var expected: Tracker? = null
        localTrackerOperations.getTrackerAsOneShot(0L).onSuccess {
            expected = it
        }
        Truth.assertThat(expected).isEqualTo(tracker)
    }

    @Test
    fun `should return error when tracker id does not exist`() = runBlocking {
        whenever(trackerDao.getTracker(0L)).thenReturn(null)
        verifyZeroInteractions(trackerMapper)
        var expected: TrackerNotFound? = null
        localTrackerOperations.getTrackerAsOneShot(0L).onFailure {
            expected = it as? TrackerNotFound
        }
        Truth.assertThat(expected).isInstanceOf(TrackerNotFound::class.java)
    }

    @Test
    fun `should save tracker`() = runBlocking {
        val cachedTracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val tracker = Tracker(
            id = 0L,
            petName = "Fluffy"
        )
        whenever(trackerMapper.mapFromDomainEntity(tracker)).thenReturn(cachedTracker)
        whenever(trackerDao.addTracker(cachedTracker)).thenReturn(1)
        val expected = localTrackerOperations.saveTracker(tracker)
        Truth.assertThat(expected).isEqualTo(Ok(Unit))
    }

    @Test
    fun `should send error when tracker already exists`() = runBlocking {
        val cachedTracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val tracker = Tracker(
            id = 0L,
            petName = "Fluffy"
        )
        var expected: TrackerInsertionWithSameId? = null
        whenever(trackerMapper.mapFromDomainEntity(tracker)).thenReturn(cachedTracker)
        whenever(trackerDao.addTracker(cachedTracker)).thenReturn(-1)
        localTrackerOperations.saveTracker(tracker).onFailure {
            expected = it as? TrackerInsertionWithSameId
        }
        Truth.assertThat(expected).isInstanceOf(TrackerInsertionWithSameId::class.java)
    }

    @Test
    fun `should delete tracker by Id`() = runBlocking {
        val cachedTracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val tracker = Tracker(
            id = 0L,
            petName = "Fluffy"
        )
        whenever(trackerMapper.mapFromDomainEntity(tracker)).thenReturn(cachedTracker)
        whenever(trackerDao.deleteByTrackerId(cachedTracker.id)).thenReturn(1)
        val expected = localTrackerOperations.deleteTracker(0L)
        Truth.assertThat(expected).isEqualTo(Ok(Unit))
    }
}