plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.example")
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}