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

package com.ulusoyapps.pettrackerapp.domain.entities.petandtracker

import android.location.Location
import java.util.concurrent.TimeUnit

data class Tracker(
    val id: Long,
    val petName: String? = null,
    val batteryPercentage: Double = 0.0,
    /**
     * The period of location update in milliseconds. Default value is 1 minute
     */
    val locationUpdatePeriodInMs: Long = TimeUnit.MINUTES.toMillis(1)
)