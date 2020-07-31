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

package com.ulusoyapps.pettrackerapp.datasource.di


import com.ulusoyapps.pettrackerapp.datasource.pet.PetDataRepository
import com.ulusoyapps.pettrackerapp.datasource.pet.datasource.PetDataSource
import com.ulusoyapps.pettrackerapp.datasource.pet.datasource.local.PetLocalDataSource
import com.ulusoyapps.pettrackerapp.datasource.petandtracker.PetAndTrackerDataRepository
import com.ulusoyapps.pettrackerapp.datasource.petandtracker.datasource.PetAndTrackerDataSource
import com.ulusoyapps.pettrackerapp.datasource.petandtracker.datasource.local.PetAndTrackerLocalDataSource
import com.ulusoyapps.pettrackerapp.datasource.tracker.TrackerDataRepository
import com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.TrackerDataSource
import com.ulusoyapps.pettrackerapp.datasource.tracker.datasource.local.TrackerLocalDataSource
import com.ulusoyapps.pettrackerapp.domain.repository.PetAndTrackerRepository
import com.ulusoyapps.pettrackerapp.domain.repository.PetRepository
import com.ulusoyapps.pettrackerapp.domain.repository.TrackerRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DatasourceModule {
    // Use @Binds to tell Dagger which implementation it needs to use when providing an interface.
    @Binds
    abstract fun providePetRepository(repository: PetDataRepository): PetRepository

    @Binds
    abstract fun providePetDataSource(datasource: PetLocalDataSource): PetDataSource

    @Binds
    abstract fun provideTrackerRepository(repository: TrackerDataRepository): TrackerRepository

    @Binds
    abstract fun provideTrackerDataSource(datasource: TrackerLocalDataSource): TrackerDataSource

    @Binds
    abstract fun providePetAndTrackerRepository(repository: PetAndTrackerDataRepository): PetAndTrackerRepository

    @Binds
    abstract fun providePetAndTrackerDataSource(datasource: PetAndTrackerLocalDataSource): PetAndTrackerDataSource
}
