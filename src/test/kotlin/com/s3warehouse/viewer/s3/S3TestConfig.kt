/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

import com.s3warehouse.viewer.config.LocalStackContainerConf
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.testcontainers.containers.localstack.LocalStackContainer
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
@Import(LocalStackContainerConf::class)
class S3TestConfig {

    @Value("\${aws.region}")
    lateinit var localstackAWSRegion : String
    @Value("\${aws.credentials.accessKey}")
    lateinit var localstackAWSAccessKey : String
    @Value("\${aws.credentials.secretKey}")
    lateinit var localstackAWSSecretKey : String
    @Value("\${localstack.services.port}")
    var localstackServicePort : Int = 0

    @Bean
    @Primary
    fun s3TestService(s3Client: S3Client) : S3TestService {
        return S3TestServiceImpl(s3Client)
    }

    @Bean
    @Primary
    fun s3TestClient(localstackContainer : LocalStackContainer, requiredServiceList : List<LocalStackContainer.Service>) : S3Client {
        localstackContainer.start()
        return S3Client
            .builder()
            /*
            TODO: Remove the lines below as soon as localstack update their LocalStackContainer.Service enum
            We need to replace the port specified in localstack's library for our service to the dynamically
            chosen exposed port mapped to the single entry point on 4566
            */
            .endpointOverride(localstackContainer.getEndpointOverride(requiredServiceList.first()).let {
                url -> URI.create(
                    url.toString().replace(
                    """[0-9]{4,5}$""".toRegex(),
                    localstackContainer.getMappedPort(localstackServicePort).toString() )) })
            // Localstack doesn't check the credentials so I left it here only because of the official guide
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(localstackAWSAccessKey, localstackAWSSecretKey)))
            .serviceConfiguration(
                S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .region(Region.of(localstackAWSRegion))
            .build()
    }
}