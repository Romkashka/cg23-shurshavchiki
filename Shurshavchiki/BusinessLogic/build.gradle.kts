plugins {
    id("java")
}

group = "ru.shurshavchiki"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("me.steinborn:libdeflate-java-core:0.1.0-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}