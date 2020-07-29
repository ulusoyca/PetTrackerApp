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

package com.ulusoyapps.unittesting

import com.google.common.truth.ThrowableSubject
import com.google.common.truth.Truth.assertThat

inline fun assertThrows(block: () -> Unit): ThrowableSubject {
    try {
        block()
    } catch (e: Throwable) {
        return assertThat(e)
    }
    throw AssertionError("Expected Throwable")
}
