/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import javax.annotation.PostConstruct

@Configuration
class LocalStackContainerConf {

    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        @Container
        lateinit var localstackContainer: LocalStackContainer
    }

    @Bean
    fun localStackContainer() : LocalStackContainer {
        return localstackContainer
    }

    @Bean
    fun requiredServiceList() : List<LocalStackContainer.Service> {
        return serviceList
    }

    lateinit var serviceList : List<LocalStackContainer.Service>

    @Value("\${localstack.services}")
    lateinit var localstackServiceArray : List<String>
    @Value("\${localstack.image.version}")
    lateinit var localstackImageVersion : String

    @PostConstruct
    fun init() {
        localstackContainer = LocalStackContainer(localstackImageVersion)
            .withServices(
                *LocalStackContainer.Service.values()
                    .filter { service -> service.name in localstackServiceArray }
                    .also { services -> serviceList = services }
                    .toTypedArray())
            .withLogConsumer { log -> logger.debug(log.utf8String.trim()) }
    }
}