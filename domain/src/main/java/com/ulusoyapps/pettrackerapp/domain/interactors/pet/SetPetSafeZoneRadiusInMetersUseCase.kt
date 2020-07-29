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

package com.ulusoyapps.pettrackerapp.domain.interactors.pet

import com.github.michaelbull.result.Result
import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.messages.PetMessage
import com.ulusoyapps.pettrackerapp.domain.repository.PetRepository


import javax.inject.Inject

class SetPetSafeZoneRadiusInMetersUseCase
@Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(petName: String, radius: Double): Result<Unit, DomainMessage> {
        return petRepository.setPetSafeZoneRadiusInMeters(petName, radius)
    }
}