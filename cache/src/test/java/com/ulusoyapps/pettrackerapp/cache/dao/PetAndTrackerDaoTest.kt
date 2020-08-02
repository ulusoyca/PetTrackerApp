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
import com.ulusoyapps.pettrackerapp.cache.entities.CachedLatLng
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet
import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker
import com.ulusoyapps.pettrackerapp.cache.entities.relationship.CachedPetAndCachedTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PetAndTrackerDaoTest : PetTrackerDatabaseTest() {

    private val petAndTrackerDao: PetAndTrackerDao
        get() = petTrackerDatabase.petAndTrackerDao()

    private val trackerDao
        get() = petTrackerDatabase.trackerDao()

    private val petDao: PetDao
        get() = petTrackerDatabase.petDao()

    @Test
    fun shouldGetCachedPetAndTracker() = runBlocking(Dispatchers.IO) {
        val pet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val tracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val actual = CachedPetAndCachedTracker(
            pet = pet,
            tracker = tracker
        )
        petDao.addPet(pet)
        trackerDao.addTracker(tracker)
        val expected = petAndTrackerDao.getCachedPetAndCachedTracker(("Fluffy"))
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return null when pet not found`() = runBlocking(Dispatchers.IO) {
        val expected = petAndTrackerDao.getCachedPetAndCachedTracker(("Fluffyx"))
        Truth.assertThat(expected).isNull()
    }

    @Test
    fun shouldGetAllCachedPetAndTracker() = runBlocking(Dispatchers.IO) {
        val pet = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val tracker = CachedTracker(
            id = 0L,
            petName = "Fluffy"
        )
        val actual = listOf(
            CachedPetAndCachedTracker(
                pet = pet,
                tracker = tracker
            )
        )
        petDao.addPet(pet)
        trackerDao.addTracker(tracker)
        val expected = petAndTrackerDao.getAllCachedPetAndCachedTrackers()
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `shouldReturnEmptyListWhenNoPetsFound`() = runBlocking(Dispatchers.IO) {
        val actual = petAndTrackerDao.getAllCachedPetAndCachedTrackers()
        Truth.assertThat(actual).isEmpty()
    }
}