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

package com.ulusoyapps.pettrackerapp.cache.mapper

import com.ulusoyapps.pettrackerapp.cache.entities.CachedLatLng
import com.ulusoyapps.pettrackerapp.domain.entities.location.LatLng
import com.ulusoyapps.pettrackerapp.domain.entities.location.Latitude
import com.ulusoyapps.pettrackerapp.domain.entities.location.Longitude
import javax.inject.Inject


class LatLngMapper
@Inject constructor() : EntityMapper<LatLng, CachedLatLng> {
    override fun mapFromDomainEntity(type: LatLng): CachedLatLng {
        return CachedLatLng(
            latitude = type.latitude.value,
            longitude = type.longitude.value
        )
    }

    override fun mapToDomainEntity(type: CachedLatLng): LatLng {
        return LatLng(
            Latitude(type.latitude),
            Longitude(type.longitude)
        )
    }

    override fun mapFromDomainEntityList(type: List<LatLng>): List<CachedLatLng> {
        return type.map { mapFromDomainEntity(it) }
    }

    override fun mapToDomainEntityList(type: List<CachedLatLng>): List<LatLng> {
        return type.map { mapToDomainEntity(it) }
    }

}