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

package com.ulusoyapps.pettrackerapp.cache.dao

import com.google.common.truth.Truth
import com.ulusoyapps.pettrackerapp.cache.dao.PetDao
import com.ulusoyapps.pettrackerapp.cache.entities.CachedLatLng
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PetDaoTest : PetTrackerDatabaseTest() {

    private val petDao: PetDao
        get() = petTrackerDatabase.petDao()

    @Test
    fun shouldInsertAndGetNewPet() = runBlocking(Dispatchers.IO) {
        val actual = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        petDao.addPet(actual)
        val expected = petDao.getPet("Fluffy")
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return null if pet is not found`() = runBlocking(Dispatchers.IO) {
        val expected = petDao.getPet("Fluffy")
        Truth.assertThat(expected).isNull()
    }

    @Test
    fun shouldInsertAndGetAllPets() = runBlocking(Dispatchers.IO) {
        val actual = listOf(
            CachedPet(
                name = "Fluffy",
                gender = 1,
                breed = "Breed",
                birthday = 0L,
                color = "",
                safeZoneCenter = CachedLatLng(0f, 0f)
            )
        )
        petDao.addPet(actual.first())
        val expected = petDao.getAllPets()
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return empty list if no pet is found`() = runBlocking(Dispatchers.IO) {
        val expected = petDao.getAllPets()
        Truth.assertThat(expected).isEmpty()
    }

    @Test
    fun `should ignore if the same id is added`() = runBlocking(Dispatchers.IO) {
        val newEntry = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val successfulResult = petDao.addPet(newEntry)
        val updatedEntry = CachedPet(
            name = "Fluffy",
            gender = 2,
            breed = "Breedx",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val failedResult = petDao.addPet(updatedEntry)
        val expected = petDao.getPet("Fluffy")
        Truth.assertThat(expected).isNotEqualTo(updatedEntry)
        Truth.assertThat(expected).isEqualTo(newEntry)
        Truth.assertThat(successfulResult).isGreaterThan(0)
        // TODO -> FIX
        //Truth.assertThat(failedResult).isEqualTo(-1)
    }

    @Test
    fun updatesPet() = runBlocking(Dispatchers.IO) {
        val newEntry = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        petDao.addPet(newEntry)
        val updatedEntry = CachedPet(
            name = "Fluffy",
            gender = 2,
            breed = "Breedx",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val numberOfRowsUpdated = petDao.update(updatedEntry)
        val expected = petDao.getPet("Fluffy")
        Truth.assertThat(expected).isNotEqualTo(newEntry)
        Truth.assertThat(expected).isEqualTo(updatedEntry)
        Truth.assertThat(numberOfRowsUpdated).isEqualTo(1)
    }

    @Test
    fun `should return 0 if pet to be updated not found`() = runBlocking(Dispatchers.IO) {
        val updatedEntry = CachedPet(
            name = "Fluffy",
            gender = 2,
            breed = "Breedx",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val numberOfRowsUpdated = petDao.update(updatedEntry)
        val expected = petDao.getPet("Fluffy")
        Truth.assertThat(expected).isNull()
        Truth.assertThat(numberOfRowsUpdated).isEqualTo(0)
    }

    @Test
    fun `should return 0 if pet to be deleted not found`() = runBlocking(Dispatchers.IO) {
        val updatedEntry = CachedPet(
            name = "Fluffy",
            gender = 2,
            breed = "Breedx",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        val numberOfRowsDeleted = petDao.delete(updatedEntry)
        Truth.assertThat(numberOfRowsDeleted).isEqualTo(0)
    }

    @Test
    fun `should return 1 if pet to be deleted is found`() = runBlocking(Dispatchers.IO) {
        val actual = CachedPet(
            name = "Fluffy",
            gender = 1,
            breed = "Breed",
            birthday = 0L,
            color = "",
            safeZoneCenter = CachedLatLng(0f, 0f)
        )
        petDao.addPet(actual)
        val numberOfRowsDeleted = petDao.delete(actual)
        Truth.assertThat(numberOfRowsDeleted).isEqualTo(1)
    }
}