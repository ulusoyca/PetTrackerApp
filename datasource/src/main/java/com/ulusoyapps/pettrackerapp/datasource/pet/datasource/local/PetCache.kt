/*
 * Copyright 2020 Cagatay Ulusoy (Ulus Oy Apps). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 *  See the License for the specific language governing permissions and limitations under the License.
 */

package com.ulusoyapps.pettrackerapp.datasource.pet.datasource.local

import com.github.michaelbull.result.Result


import com.ulusoyapps.pettrackerapp.domain.entities.messages.DomainMessage
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet
import kotlinx.coroutines.flow.Flow

interface PetCache {
    suspend fun getAllPets(): Flow<Result<List<Pet>, DomainMessage>>
    suspend fun getPet(name: String): Flow<Result<Pet, DomainMessage>>
    suspend fun getPetAsOneShot(name: String): Result<Pet, DomainMessage>
    suspend fun savePet(pet: Pet): Result<Unit, DomainMessage>
    suspend fun updatePet(pet: Pet): Result<Unit, DomainMessage>
    suspend fun removePet(pet: Pet): Result<Unit, DomainMessage>
}