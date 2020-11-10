/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

import software.amazon.awssdk.http.SdkHttpResponse

interface S3TestService : S3Service {

    fun createBucket(bucketName: String) : SdkHttpResponse

    fun s3cleanup()

}