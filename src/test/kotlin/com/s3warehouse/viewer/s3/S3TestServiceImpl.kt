/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import software.amazon.awssdk.http.SdkHttpResponse
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.CreateBucketRequest
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request

@Service
class S3TestServiceImpl(s3Client: S3Client) : S3ServiceImpl(s3Client), S3TestService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun createBucket(bucketName: String) : SdkHttpResponse {
        return s3Client
            .createBucket(CreateBucketRequest.builder().bucket(bucketName).build())
            .sdkHttpResponse()
            .also { x -> logger.debug("IT AWS S3 creating bucket \"$bucketName\" code ${x.statusCode()}") }
    }

    override fun s3cleanup() {
        logger.debug("Cleanup S3 everything after every test")
        s3Client
            .listBuckets()
            .buckets()
            .forEach { bucket -> s3Client
                .listObjectsV2(
                    ListObjectsV2Request.builder().bucket(bucket.name()).build()).contents()
                .also { logger.debug("IT AWS S3 exploring bucket \"${bucket.name()}\"") }
                .forEach { file -> s3Client
                    .deleteObject(
                        DeleteObjectRequest.builder().bucket(bucket.name()).key(file.key()).build())
                    .also { logger.debug("IT AWS S3 file \"${file.key()}\" deleted") } }
                .also { s3Client
                    .deleteBucket(
                        DeleteBucketRequest.builder().bucket(bucket.name()).build())}
                .also { logger.debug("IT AWS S3 bucket \"${bucket.name()}\" deleted") }}
    }
}