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

package com.ulusoyapps.pettrackerapp.cache.dao

import com.google.common.truth.Truth
import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TrackerDaoTest : PetTrackerDatabaseTest() {

    private val trackerDao
        get() = petTrackerDatabase.trackerDao()

    @Test
    fun shouldInsertAndGetNewTracker() = runBlocking(Dispatchers.IO) {
        val actual = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        trackerDao.addTracker(actual)
        val expected = trackerDao.getTracker(0L)
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return null if pet is not found`() = runBlocking(Dispatchers.IO) {
        val expected = trackerDao.getTracker(0L)
        Truth.assertThat(expected).isNull()
    }


    @Test
    fun shouldGetTrackerFromPetId() = runBlocking(Dispatchers.IO) {
        val actual = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        trackerDao.addTracker(actual)
        val expected = trackerDao.getTrackerByPetName("Fluffy")
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should ignore if the same id is added`() = runBlocking(Dispatchers.IO) {
        val newEntry = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val updatedEntry = CachedTracker(
            id = 0L,
            petName = "Fluffy2"
        )
        val successfulResult = trackerDao.addTracker(newEntry)
        val failedResult = trackerDao.addTracker(updatedEntry)
        val expected = trackerDao.getTracker(0L)
        Truth.assertThat(expected).isNotEqualTo(updatedEntry)
        Truth.assertThat(expected).isEqualTo(newEntry)
        Truth.assertThat(successfulResult).isGreaterThan(-1)
        // TODO -> FIX
        //Truth.assertThat(failedResult).isEqualTo(-1)
    }

    @Test
    fun updatesTracker() = runBlocking(Dispatchers.IO) {
        val newEntry = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val updatedEntry = CachedTracker(
            id = 0L,
            petName = "Fluffy2"
        )
        trackerDao.addTracker(newEntry)
        val newExpected = trackerDao.getTracker(0L)
        Truth.assertThat(newExpected).isEqualTo(newEntry)


        val numberOfRowsUpdated = trackerDao.update(updatedEntry)
        val updatedExpected = trackerDao.getTracker(0L)
        Truth.assertThat(updatedExpected).isNotEqualTo(newEntry)
        Truth.assertThat(updatedExpected).isEqualTo(updatedEntry)
        Truth.assertThat(numberOfRowsUpdated).isEqualTo(1)
    }

    @Test
    fun `should delete the tracker`() = runBlocking(Dispatchers.IO) {
        val newEntry = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        trackerDao.addTracker(newEntry)
        val actual = trackerDao.getTracker(0L)
        Truth.assertThat(actual).isEqualTo(newEntry)

        trackerDao.delete(newEntry)
        val removed = trackerDao.getTracker(0L)
        Truth.assertThat(removed).isNull()
    }

    @Test
    fun `should delete the entry by id`() = runBlocking(Dispatchers.IO) {
        val newEntry = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        trackerDao.addTracker(newEntry)
        val expected = trackerDao.getTracker(0L)
        Truth.assertThat(expected).isEqualTo(newEntry)

        trackerDao.deleteByTrackerId(0L)
        val removed = trackerDao.getTracker(0L)
        Truth.assertThat(removed).isNull()
    }

    @Test
    fun `should return 0 if tracker to be updated not found`() = runBlocking(Dispatchers.IO) {
        val updatedEntry = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val numberOfRowsUpdated = trackerDao.update(updatedEntry)
        val expected = trackerDao.getTracker(0L)
        Truth.assertThat(expected).isNull()
        Truth.assertThat(numberOfRowsUpdated).isEqualTo(0)
    }
}