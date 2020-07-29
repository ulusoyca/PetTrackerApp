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

/**
 * Pet and tracker has 1 to 1 relationship. This class holds the tracker and the pet who wears that particular tracker
 */
data class PetAndTracker(
    val pet: Pet,
    val tracker: Tracker?
)