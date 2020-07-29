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

import androidx.room.*
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPet(pet: CachedPet): Long

    @Query("SELECT * FROM CachedPet")
    suspend fun getAllPets(): List<CachedPet>

    @Query("SELECT * FROM CachedPet WHERE name = :name")
    suspend fun getPet(name: String): CachedPet?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pet: CachedPet): Int

    @Delete
    suspend fun delete(pet: CachedPet): Int
}