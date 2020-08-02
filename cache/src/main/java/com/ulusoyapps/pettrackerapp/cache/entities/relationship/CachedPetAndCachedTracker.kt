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

package com.ulusoyapps.pettrackerapp.cache.entities.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet
import com.ulusoyapps.pettrackerapp.cache.entities.CachedTracker

data class CachedPetAndCachedTracker(
    @Embedded val pet: CachedPet,
    @Relation(
        parentColumn = "name",
        entityColumn = "petName"
    )
    val tracker: CachedTracker?
)