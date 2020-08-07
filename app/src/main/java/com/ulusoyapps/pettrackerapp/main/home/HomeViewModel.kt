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

package com.ulusoyapps.pettrackerapp.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onSuccess
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.PetAndTracker
import com.ulusoyapps.pettrackerapp.domain.interactors.petandtracker.GetAllPetAndTrackersUseCase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel
@Inject constructor(
    private val getAllPetAndTrackersUseCase: GetAllPetAndTrackersUseCase
) : ViewModel() {

    private val _nextScreen = MutableLiveData<NextScreen>()
    val nextScreen: LiveData<NextScreen>
        get() = _nextScreen

    private val _petsAndTrackers = MutableLiveData<List<PetAndTracker>>()
    val petsAndTrackers: LiveData<List<PetAndTracker>>
        get() = _petsAndTrackers

    fun getAllPets() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllPetAndTrackersUseCase().collect { result ->
                result.onSuccess { petsAndTrackers ->
                    _petsAndTrackers.postValue(petsAndTrackers)
                }
            }
        }
    }

    fun onAddPetClick() {
        // _nextScreen.value = AddPetScreen()
    }

    fun onCardClick(petAndTracker: PetAndTracker) {
        if (petAndTracker.tracker == null) {
            _nextScreen.value = AddTrackerScreen
        } else {
            _nextScreen.value = TrackPetScreen(petAndTracker.pet.name)
        }
    }

    fun onPetEdit(petName: String) {
        _nextScreen.value = AddPetScreen(petName)
    }

    fun onNavigatedToNextScreen() {
        _nextScreen.value = NoAction
    }
}
