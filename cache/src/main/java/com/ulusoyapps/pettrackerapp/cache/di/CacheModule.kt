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

package com.ulusoyapps.pettrackerapp.cache.di

import android.content.Context
import androidx.room.Room
import com.ulusoyapps.pettrackerapp.cache.dao.PetAndTrackerDao
import com.ulusoyapps.pettrackerapp.cache.dao.PetDao
import com.ulusoyapps.pettrackerapp.cache.dao.TrackerDao
import com.ulusoyapps.pettrackerapp.cache.database.PetTrackerDatabase
import com.ulusoyapps.pettrackerapp.cache.datasources.LocalPetAndTrackerOperations
import com.ulusoyapps.pettrackerapp.cache.datasources.LocalPetOperations
import com.ulusoyapps.pettrackerapp.cache.datasources.LocalTrackerOperations
import com.ulusoyapps.pettrackerapp.datasource.pet.datasource.local.PetCache
import com.ulusoyapps.pettrackerapp.datasource.petandtracker.datasource.local.PetAndTrackerCache
import com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.local.TrackerCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {
    // Use @Binds to tell Dagger which implementation it needs to use when providing an interface.
    @Binds
    abstract fun providePetLocalDataSource(localOperations: LocalPetOperations): PetCache

    @Binds
    abstract fun provideTrackerLocalDataSource(localOperations: LocalTrackerOperations): TrackerCache

    @Binds
    abstract fun providePetAndTrackerLocalDataSource(localOperations: LocalPetAndTrackerOperations): PetAndTrackerCache

    companion object {

        @Provides
        fun provideRoomDatabase(context: Context): PetTrackerDatabase {
            return Room.databaseBuilder(
                context,
                PetTrackerDatabase::class.java, "pet_tracker.db"
            ).build()
        }

        @Provides
        fun providePetAndTrackerDao(petTrackerDatabase: PetTrackerDatabase): PetAndTrackerDao {
            return petTrackerDatabase.petAndTrackerDao()
        }

        @Provides
        fun provideTrackerDao(petTrackerDatabase: PetTrackerDatabase): TrackerDao {
            return petTrackerDatabase.trackerDao()
        }

        @Provides
        fun providePetDao(petTrackerDatabase: PetTrackerDatabase): PetDao {
            return petTrackerDatabase.petDao()
        }
    }
}
