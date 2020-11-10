# One-stop guide: Kotlin + Spring Boot 2 + AWS S3 + Testcontainers
  
### :bulb: &nbsp; Idea

Application implements the sample of CR~~U~~D REST API for AWS S3 (Simple Storage Service) to list, upload and download objects of buckets. Service covered by an integration test which starts AWS S3 mock inside Docker container using Localstack.   

### :gear: &nbsp; Components
* Core: 
  * [Kotlin 1.4](https://kotlinlang.org/docs/reference/whatsnew14.html) and target JDK 11
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
| SERVER_PORT | Listening port of embedded Tomcat | 8080 |
| MAX_FILE_SIZE | Maximum size of each uploading file of request | 10MB |
| MAX_REQUEST_SIZE | Maximum size of request | 10MB |
| AWS_REGION | Listening port of embedded Tomcat | us-east-1 |
| AWS_ACCESS_KEY | [Access key](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html) for an AWS IAM user |  |
| AWS_SECRET_KEY | Secret key for the Access Key |  |
| SWAGGER_ENABLED | Switch on the JSON and GUI documentation for the REST API | false |

### Main commands
  
| Action | CLI command |
|---|---|
| Run app | ```./gradlew bootRun --args='--AWS_ACCESS_KEY=VALUE --AWS_SECRET_KEY=VALUE'``` * |
| Run tests | ```./gradlew test```  |
| Build Docker image | ```./gradlew bootBuildImage``` |
| [Run](https://docs.docker.com/engine/reference/commandline/run/) container | ```docker run -dit -e AWS_ACCESS_KEY=VALUE -e AWS_SECRET_KEY=VALUE -p 8080:8080 antukhov/legallery:0.1``` * |

[ **&ast;** ] - don't forget to change values of environment variables

## REST API documentation

| Format | URL |
|---|---|
| HTML | http://localhost:8080/swagger-ui/index.html * |
| JSON | http://localhost:8080/v2/api-docs * |

[ **&ast;** ] - don't forget to change hostname and port if needed

## Support

If you need any kind of support don't hesitate to let me know :wink:
  
Created by [Alex Antukhov](https://www.linkedin.com/in/antukhov/) 