plugins {
    java
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.6.0"
}

group = "gordwilling"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("javax.inject:javax.inject:1")
    implementation("org.apache.commons:commons-jexl3:3.2.1")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.8")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApi {
    apiDocsUrl.set("http://localhost:8080/api/v3/api-docs")
    outputDir.set(file("$buildDir/docs"))
    waitTimeInSeconds.set(10)
}
