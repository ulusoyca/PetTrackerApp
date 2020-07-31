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

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import com.ulusoyapps.pettrackerapp.cache.entities.CachedPet
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Gender
import com.ulusoyapps.pettrackerapp.domain.entities.petandtracker.Pet
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class PetMapper
@Inject constructor(
    private val latLngMapper: LatLngMapper,
    private val locationMapper: LocationMapper
) : EntityMapper<Pet, CachedPet> {
    override fun mapFromDomainEntity(type: Pet): CachedPet {
        return CachedPet(
            name = type.name,
            breed = type.breed,
            gender = type.gender.id,
            birthday = type.birthday,
            picture = fromBitmapToBase64(type.picture),
            safeZoneCenter = type.safeZoneCenter.let { latLngMapper.mapFromDomainEntity(it) },
            safeZoneRadius = type.safeZoneRadius,
            alarmEnabled = type.alarmEnabled,
            color = type.color,
            lastLocation = type.lastLocation?.let { locationMapper.mapFromDomainEntity(it) }
        )
    }

    override fun mapToDomainEntity(type: CachedPet): Pet {
        return Pet(
            name = type.name,
            breed = type.breed,
            gender = Gender.fromId(type.gender),
            picture = fromBase64ToBitmap(type.picture),
            safeZoneCenter = type.safeZoneCenter.let { latLngMapper.mapToDomainEntity(it) },
            safeZoneRadius = type.safeZoneRadius,
            alarmEnabled = type.alarmEnabled,
            color = type.color,
            birthday = type.birthday,
            lastLocation = type.lastLocation?.let { locationMapper.mapToDomainEntity(it) }
        )
    }

    override fun mapFromDomainEntityList(type: List<Pet>): List<CachedPet> {
        return type.map { mapFromDomainEntity(it) }
    }

    override fun mapToDomainEntityList(type: List<CachedPet>): List<Pet> {
        return type.map { mapToDomainEntity(it) }
    }

    private fun fromBase64ToBitmap(base64Value: String?): Bitmap? {
        return if (!TextUtils.isEmpty(base64Value)) {
            val decodedBytes: ByteArray = Base64.decode(base64Value, 0)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } else {
            null
        }
    }

    private fun fromBitmapToBase64(bitmap: Bitmap?): String? {
        return if (bitmap != null) {
            val byteArrayOS = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 25, byteArrayOS)
            Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
        } else {
            null
        }
    }
}