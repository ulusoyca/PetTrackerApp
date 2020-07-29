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

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ulusoyapps.pettrackerapp.cache.entities.relationship.CachedPetAndCachedTracker

@Dao
interface PetAndTrackerDao {
    @Transaction
    @Query("SELECT * FROM CachedPet WHERE name = :name")
    fun getCachedPetAndCachedTracker(name: String): CachedPetAndCachedTracker?

    @Transaction
    @Query("SELECT * FROM CachedPet")
    fun getAllCachedPetAndCachedTrackers(): List<CachedPetAndCachedTracker>
}