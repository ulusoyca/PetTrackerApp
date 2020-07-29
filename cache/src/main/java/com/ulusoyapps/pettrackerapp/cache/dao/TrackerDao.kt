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
import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker

@Dao
interface TrackerDao {
    @Query("SELECT * FROM CachedTracker WHERE id = :trackerId")
    suspend fun getTracker(trackerId: Long): CachedTracker?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTracker(tracker: CachedTracker): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(tracker: CachedTracker): Int

    @Delete
    suspend fun delete(tracker: CachedTracker): Int

    @Query("DELETE FROM CachedTracker WHERE id = :trackerId")
    suspend fun deleteByTrackerId(trackerId: Long): Int

    @Query("SELECT * FROM CachedTracker WHERE petName = :petName")
    suspend fun getTrackerByPetName(petName: String): CachedTracker?
}