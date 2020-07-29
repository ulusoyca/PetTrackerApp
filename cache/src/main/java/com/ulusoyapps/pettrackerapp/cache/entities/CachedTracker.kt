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

package com.ulusoyapps.pettrackerapp.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.concurrent.TimeUnit

/**
 * One to one relationship with Pet
 */
@Entity
data class CachedTracker(
    @PrimaryKey val id: Long,
    val petName: String?,
    val batteryPercentage: Double = 100.0,
    /**
     * The period of location update in milliseconds. Default value is 1 minute
     */
    val locationUpdatePeriodInMs: Long = TimeUnit.MINUTES.toMillis(1)
)