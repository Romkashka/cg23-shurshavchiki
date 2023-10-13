plugins {
    id("java")
}

group = "ru.shurshavchiki"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation(project(mapOf("path" to ":BusinessLogic")))
}

tasks.test {
    useJUnitPlatform()
}