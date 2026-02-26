plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":library"))
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClass = "tools.kenjiotsuka.sample.MainKt"
}

kotlin {
    jvmToolchain(21)
}
