/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.LinkedMultiValueMap

@ExtendWith(SpringExtension::class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class S3IntegrationTest @Autowired constructor( var s3Service: S3TestService, var testRestTemplate: TestRestTemplate) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @LocalServerPort
    protected var port: Int = 0

    @BeforeEach
    fun beforeEach() {
        s3Service.s3cleanup()
        logger.debug("Action before each of tests in class")
    }

    @Test
    fun `GIVEN uploading 2 files WHEN files uploaded THEN response has results for both`() {
        s3Service.createBucket("test-bucket")
        val headers = LinkedMultiValueMap<String, String>()
            .also { map -> map.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE) }
        val httpEntity = HttpEntity<Any>(
            LinkedMultiValueMap<String, Any>().also { map -> map.addAll("files", listOf(
                ClassPathResource("assets/S3DemoDocument.txt"),
                ClassPathResource("assets/S3DemoPicture.jpg")
            ))}
        , headers)
        val responseEntity = testRestTemplate.exchange<Array<S3BulkResponseEntity>>(
            "http://localhost:$port/s3/test-bucket", HttpMethod.POST, httpEntity)
        assert(responseEntity.body?.size ?: 0 == 2)
        s3Service.getBucketFileList("test-bucket")
            .also { list -> assert(list.size == 2) }
            .forEach { file -> logger.info("test-bucket: $file") }
    }

}