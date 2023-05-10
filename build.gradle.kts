plugins {
    java
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.flameshine"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val sphinxVersion = "5prealpha"
val lombokVersion = "1.18.26"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.cloud:spring-cloud-gcp-starter:1.2.8.RELEASE")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")
    implementation("net.sf.phat:sphinx4-core:$sphinxVersion")
    implementation("net.sf.phat:sphinx4-data:$sphinxVersion")
    implementation("com.google.cloud:google-cloud-speech:4.11.0")
    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("com.google.guava:guava:31.1-jre")

    compileOnly("org.projectlombok:lombok:$lombokVersion")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}