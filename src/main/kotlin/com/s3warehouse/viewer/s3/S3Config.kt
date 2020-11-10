/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.time.Duration

@Configuration
class S3Config {

    @Value("\${aws.region}")
    lateinit var awsRegion : String
    @Value("\${aws.credentials.accessKey}")
    lateinit var accessKey : String
    @Value("\${aws.credentials.secretKey}")
    lateinit var secretKey : String

    @Bean(destroyMethod = "close")
    fun s3Client() : S3Client {
        return S3Client
            .builder()
            .overrideConfiguration(ClientOverrideConfiguration.builder().apiCallTimeout(Duration.ofSeconds(10)).build())
            .region(Region.regions().find {region -> region.toString() == awsRegion } )
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .build()
    }
}