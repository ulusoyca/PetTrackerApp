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

package com.ulusoyapps.pettrackerapp.cache.database

import com.ulusoyapps.pettrackerapp.cache.dao.PetAndTrackerDao
import com.ulusoyapps.pettrackerapp.cache.dao.PetDao
import com.ulusoyapps.pettrackerapp.cache.dao.TrackerDao
import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet

@Database(entities = [CachedPet::class, CachedTracker::class], version = 1, exportSchema = true)
abstract class PetTrackerDatabase : RoomDatabase() {
    abstract fun petAndTrackerDao(): PetAndTrackerDao
    abstract fun petDao(): PetDao
    abstract fun trackerDao(): TrackerDao
}