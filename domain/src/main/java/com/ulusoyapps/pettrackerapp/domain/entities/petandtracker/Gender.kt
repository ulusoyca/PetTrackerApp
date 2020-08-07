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

enum class Gender(val id: Int) {
    MALE(id = 1),
    FEMALE(id = 2);

    companion object {

        private val all = values().clone()

        fun fromId(id: Int): Gender {
            all.forEach { if (id == it.id) return it }
            throw  IllegalArgumentException("Id not found")
        }
    }
}
