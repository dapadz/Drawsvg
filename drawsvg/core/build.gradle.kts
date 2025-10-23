plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("maven-publish")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.github.dapadz"
            artifactId = "drawsvg-core"
            version = "0.0.5"
            from(components["java"])
            pom { name.set("drawsvg-core") }
        }
    }
}