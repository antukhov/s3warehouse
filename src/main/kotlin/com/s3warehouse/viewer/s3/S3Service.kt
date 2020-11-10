/*
 * Copyright (c) 2020 Alex Antukhov
 */

package com.s3warehouse.viewer.s3

import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.http.SdkHttpResponse

interface S3Service {

    fun getBucketFileList(bucketName: String) : List<String>

    fun downloadFile(bucketName: String, fileKey: String) : ByteArray

    fun deleteFile(bucketName: String, fileKey: String) : SdkHttpResponse

    fun uploadFiles(bucketName: String, files: Array<MultipartFile>) : List<S3BulkResponseEntity>

}