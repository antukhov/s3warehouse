# One-stop guide: Kotlin + Spring Boot 2 + AWS S3 + Testcontainers

[![Build Status](https://travis-ci.org/antukhov/s3warehouse.svg?branch=master)](https://travis-ci.org/antukhov/s3warehouse)
[![codecov](https://codecov.io/gh/antukhov/s3warehouse/branch/master/graph/badge.svg?token=FAH13GXHFN)](https://codecov.io/gh/antukhov/s3warehouse)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=antukhov_s3warehouse&metric=alert_status)](https://sonarcloud.io/dashboard?id=antukhov_s3warehouse)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
  
### :bulb: &nbsp; Idea

Application implements the sample of CR~~U~~D REST API for AWS S3 (Simple Storage Service) to list, upload and download objects of buckets. Service covered by an integration test which starts AWS S3 mock inside Docker container using Localstack.   

### :gear: &nbsp; Components
* Core: 
  * [Kotlin 1.4](https://kotlinlang.org/docs/reference/whatsnew14.html), JDK 11
  * Spring Boot 2.4  
  * Gradle and [OCI](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#build-image) - Docker image assembling  
  * [AWS SDK for Java 2.0](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/welcome.html) with S3 integration test starter  
* Testing:
  * JUnit 5
  * [Testcontainers](https://www.testcontainers.org/modules/localstack/) - library to use Docker in tests
  * [Localstack](https://github.com/localstack/localstack) - mocking framework to test AWS cloud services
* Utils:
  * Swagger 2  
  * [Spring Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)
  
### :briefcase: &nbsp; Goal
  
Being accelerator for learning and diving into the best cutting edge tools listed above by demonstrating how they can work together.  
  
## Deployment

### Environment variables

| Variable | Description | Default value |
|---|---|---|
| **AWS** | | |
| AWS_REGION | Listening port of embedded Tomcat | us-east-1 |
| AWS_ACCESS_KEY | [Access key](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html) for an AWS IAM user |  |
| AWS_SECRET_KEY | Secret key for the Access Key |  |
| **WEB-SERVER** | | |
| SERVER_PORT | Listening port of embedded Tomcat | 8080 |
| MAX_FILE_SIZE | Maximum size of each uploading file of request | 10MB |
| MAX_REQUEST_SIZE | Maximum size of request | 10MB |
| **DEBUG** | | |
| SWAGGER_ENABLED | Switch on the JSON and GUI documentation for the REST API | false |
| LOG_FILE | Set the relative or absolute path to the log file | s3warehouse.log |
| ACTUATOR_LOG_FILE | Provides an access to log file through the /status/logfile | false |

### Main commands
  
| Action | CLI command |
|---|---|
| Run app | ```./gradlew bootRun --args='--AWS_ACCESS_KEY=VALUE --AWS_SECRET_KEY=VALUE'``` * |
| Run tests | ```./gradlew test```  |
| Build Docker image | ```./gradlew bootBuildImage``` |
| [Run](https://docs.docker.com/engine/reference/commandline/run/) container | ```docker run -dit -e AWS_ACCESS_KEY=VALUE -e AWS_SECRET_KEY=VALUE -p 8080:8080 antukhov/s3warehouse:0.1``` * |

[ **&ast;** ] - don't forget to change values of environment variables

### Quick start with the ready-to-use Docker

```
docker run -dit \
-e AWS_REGION=REGION_VALUE \
-e AWS_ACCESS_KEY=KEY_VALUE \
-e AWS_SECRET_KEY=SECRET_KEY \
-e SWAGGER_ENABLED=true \
-p 8085:8080 \
docker.pkg.github.com/antukhov/s3warehouse/s3warehouse:0.1
```

## REST API documentation

| Format | URL |
|---|---|
| HTML | http://localhost:8080/swagger-ui/index.html * |
| JSON | http://localhost:8080/v2/api-docs * |

[ **&ast;** ] - don't forget to change hostname and port if needed

## Support

If you need any kind of support don't hesitate to let me know :wink:
  
Created by [Alex Antukhov](https://www.linkedin.com/in/antukhov/) 