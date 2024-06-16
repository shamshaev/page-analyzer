plugins {
    application
    checkstyle
    jacoco
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass = "hexlet.code.App"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("io.javalin:javalin:6.1.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.h2database:h2:2.2.220")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("gg.jte:jte:3.1.9")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport { reports { xml.required.set(true) } }