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

import android.graphics.Bitmap
import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.location.Location

data class Pet(
    val name: String,
    val breed: String,
    val birthday: Long,
    val gender: Gender,
    val picture: Bitmap? = null,
    val safeZoneCenter: LatLng,
    /**
     * The radius of the geofence in meters. Default
     */
    val safeZoneRadius: Double = 200.0,
    val alarmEnabled: Boolean = true,
    /**
     * RGBA color code in [String] format starting with `#`. Example: `#00FF00`
     */
    val color: String,
    /**
     * Last known location of the pet
     */
    val lastLocation: Location? = null
)