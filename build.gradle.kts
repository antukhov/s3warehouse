/*
 * Copyright (c) 2020 Alex Antukhov
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.10"
	kotlin("plugin.spring") version "1.4.10"
}

group = "com.s3warehouse"
version = "0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["testcontainersVersion"] = "1.14.3"

dependencies {

	// Spring & Kotlin
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// AWS SDK v2, S3
	implementation(platform("software.amazon.awssdk:bom:2.15.14"))
	implementation ("software.amazon.awssdk:s3:2.15.14")

	// Spring test starter & Testcontainers & localstack
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.7.0"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")

	// Testcontainers & Localstack
	testImplementation("org.testcontainers:junit-jupiter:1.14.3")
	testImplementation("org.testcontainers:localstack:1.14.3")
	// Necessary to link it for avoiding the ClassNotFoundException with com.amazonaws.auth.AWSCredentials
	// Localstack still depends on it
	testImplementation("com.amazonaws:aws-java-sdk-core:1.11.887")

	// Health checking
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Swagger
	implementation("io.springfox:springfox-boot-starter:3.0.0")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
	environment = mapOf("BP_JVM_VERSION" to "11.*")
	imageName = "antukhov/${project.name}:${project.version}"
}