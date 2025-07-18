plugins {
  id("java-library")
  id("maven-publish")
}

group = "dev.hireben.demo"
version = "1.0.0-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  // Spring dependencies - provided by consuming projects
  compileOnly("org.springframework.boot:spring-boot:3.5.3")
  compileOnly("org.springframework:spring-webmvc:6.2.8")
  compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
  compileOnly("jakarta.validation:jakarta.validation-api:3.1.1")
  compileOnly("com.fasterxml.jackson.core:jackson-databind:2.19.1")
  compileOnly("org.slf4j:slf4j-api:2.0.17")
    
  // JWT dependencies - API is transitive to consuming projects
  api("io.jsonwebtoken:jjwt-api:0.12.6")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    
  // Lombok - annotation processing
  compileOnly("org.projectlombok:lombok:1.18.38")
  annotationProcessor("org.projectlombok:lombok:1.18.38")
    
  // Test dependencies
  testImplementation("org.junit.jupiter:junit-jupiter:5.12.1")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    
  // Test dependencies that need Spring context for testing
  testImplementation("org.springframework.boot:spring-boot:3.5.3")
  testImplementation("org.springframework:spring-webmvc:6.2.8")
  testImplementation("com.fasterxml.jackson.core:jackson-databind:2.19.1")
  testImplementation("org.slf4j:slf4j-api:2.0.17")
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
            
      groupId = project.group.toString()
      artifactId = project.name
      version = project.version.toString()
    }
  }
    
  repositories {
    mavenLocal()
  }
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}

tasks.register("install") {
  dependsOn("publishToMavenLocal")
  description = "Install library to local .m2 repository"
  group = "publishing"
}