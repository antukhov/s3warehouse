/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

data class S3BulkResponseEntity constructor(
    var bucket : String,
    var fileKey : String,
    var originFileName : String,
    var successful: Boolean,
    var statusCode: Int)