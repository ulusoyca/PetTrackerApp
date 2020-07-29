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

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * One to one relationship with Tracker.
 */
@Entity
data class CachedPet(
    @PrimaryKey val name: String,
    val breed: String,
    val gender: Int,
    val birthday: Long,
    val picture: String? = null,
    @Embedded(prefix = "safe_") val safeZoneCenter: CachedLatLng,
    val safeZoneRadius: Double = 200.0,
    val alarmEnabled: Boolean = true,
    val color: String,
    /**
     * Last known location of the pet
     */
    @Embedded val lastLocation: CachedLocation? = null
)