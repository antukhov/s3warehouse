/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.exception

import java.util.*

data class GeneralExceptionEntity constructor(var timestamp : Date, var message : String, var details : String)