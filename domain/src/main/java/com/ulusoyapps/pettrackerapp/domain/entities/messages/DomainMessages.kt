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

package com.ulusoyapps.pettrackerapp.domain.entities.messages

sealed class DomainMessage

open class PetAndTrackerMessage: DomainMessage()

open class TrackerMessage: DomainMessage()
class TrackerNotFound: TrackerMessage()
class TrackerInsertionWithSameId: TrackerMessage()

open class PetMessage: DomainMessage()
class PetNotFound: PetMessage()
class PetInsertionWithSameId: PetMessage()

object MockError: DomainMessage()