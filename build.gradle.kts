plugins {
    kotlin("jvm") version "2.2.20"
}

group = "ru.stimmax"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.hamcrest:hamcrest:2.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}