// Root build: shared configuration for all submodules
// group and version are defined in gradle.properties
plugins {
    kotlin("jvm") apply false
}

subprojects {
    repositories {
        mavenCentral()
    }
}
