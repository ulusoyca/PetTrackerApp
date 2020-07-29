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

package com.ulusoyapps.pettrackerapp.cache.mapper

import com.ulusoyapps.pettrackerapp.cache.entities.CachedLocation
import com.ulusoyapps.pettrackerapp.domain.entities.location.Location
import javax.inject.Inject


class LocationMapper
@Inject constructor(
    private val latLngMapper: LatLngMapper
) : EntityMapper<Location, CachedLocation> {

    override fun mapFromDomainEntity(type: Location): CachedLocation {
        return CachedLocation(
            latLng = latLngMapper.mapFromDomainEntity(type.latLng),
            timestamp = type.timestamp
        )
    }

    override fun mapToDomainEntity(type: CachedLocation): Location {
        return Location(
            latLng = latLngMapper.mapToDomainEntity(type.latLng),
            timestamp = type.timestamp
        )
    }

    override fun mapFromDomainEntityList(type: List<Location>): List<CachedLocation> {
        return type.map { mapFromDomainEntity(it) }
    }

    override fun mapToDomainEntityList(type: List<CachedLocation>): List<Location> {
        return type.map { mapToDomainEntity(it) }
    }

}