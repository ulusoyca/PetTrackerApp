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

package com.ulusoyapps.pettrackerapp

import com.ulusoyapps.pettrackerapp.cache.di.CacheModule
import com.ulusoyapps.pettrackerapp.datasource.di.DatasourceModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Application component refers to application level modules only
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ContributeActivityModule::class,
        ViewModelModule::class,
        CacheModule::class,
        DatasourceModule::class
    ]
)
interface AppComponent : AndroidInjector<PetTrackerApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<PetTrackerApp>
}
