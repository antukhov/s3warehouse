/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/s3/")
@Api(value = "AWS S3",  description = "REST API for S3", tags = ["AWS S3"])
class S3Controller(var s3service: S3Service) {

    @ApiOperation(value="List files")
    @GetMapping("{bucket}")
    fun getFileList(
        @PathVariable("bucket") bucket: String): ResponseEntity<List<String>> {
        return ResponseEntity.of(Optional.of(s3service.getBucketFileList(bucket)))
    }

    @ApiOperation(value="Delete file")
    @DeleteMapping("{bucket}/{file}")
    fun deleteFile(
        @PathVariable("bucket") bucket: String,
        @PathVariable("file") fileKey: String): ResponseEntity<Any?> {
        val responseEntity = s3service.deleteFile(bucket, fileKey)
        return ResponseEntity(responseEntity, HttpStatus.valueOf(responseEntity.statusCode()))
    }

    @ApiOperation(value="Download file")
    @GetMapping("{bucket}/{file}")
    fun downloadFile(
        @PathVariable("bucket") bucket: String,
        @PathVariable("file") fileKey: String): ResponseEntity<ByteArray> {
            val sourceFile : ByteArray = s3service.downloadFile(bucket, fileKey)
            return ResponseEntity.ok().contentLength(sourceFile.size.toLong())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"$fileKey\"")
                .body(sourceFile)
    }

    @ApiOperation(value="Upload files")
    @ApiImplicitParams(*[
        ApiImplicitParam(value = "AWS Bucket name", name = "bucket", dataType = "String", paramType = "query"),
        ApiImplicitParam(value = "Files", required = true, name = "files", allowMultiple = true,  dataType = "File", paramType = "form")
    ])
    @PostMapping("{bucket}")
    fun uploadFile(
            @PathVariable("bucket") bucket: String,
            @RequestPart("files") uploadFiles : Array<MultipartFile>
    ): ResponseEntity<Any?> {
        val awsResponse = s3service.uploadFiles(bucket, uploadFiles)
        return ResponseEntity(awsResponse, HttpStatus.OK)
    }

}