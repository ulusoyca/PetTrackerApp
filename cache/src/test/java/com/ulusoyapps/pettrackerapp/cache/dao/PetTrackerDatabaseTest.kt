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

package com.ulusoyapps.pettrackerapp.cache.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ulusoyapps.pettrackerapp.cache.database.PetTrackerDatabase
import com.ulusoyapps.unittesting.BaseArchTest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
abstract class PetTrackerDatabaseTest : BaseArchTest() {

    protected lateinit var petTrackerDatabase: PetTrackerDatabase

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        petTrackerDatabase =
            Room.inMemoryDatabaseBuilder(context, PetTrackerDatabase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        petTrackerDatabase.close()
    }
}