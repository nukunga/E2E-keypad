plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") // Spring Web
    implementation("org.springframework.boot:spring-boot-starter-security") // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-data-redis") // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-validation") // Spring Validation
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // Jackson Kotlin 모듈
    implementation("org.jetbrains.kotlin:kotlin-reflect") // Kotlin Reflection
    runtimeOnly("com.h2database:h2") // H2 데이터베이스 (테스트 용도)
    testImplementation("org.springframework.boot:spring-boot-starter-test") // Spring Boot 테스트 의존성
    testImplementation("org.springframework.security:spring-security-test") // Spring Security 테스트 의존성
    testImplementation(kotlin("test")) // Kotlin 테스트 의존성
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
