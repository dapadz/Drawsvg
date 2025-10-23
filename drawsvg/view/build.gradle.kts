plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.dapadz.drawsvg.view"
    compileSdk { version = release(36) }
    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(project(":drawsvg:core"))
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("Release") {
                groupId = "com.dapadz"
                artifactId = "drawsvg"
                version = "1.0.0"
                pom { name.set("drawsvg") }
                from(components["release"])
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/dapadz/Drawsvg")
                credentials {
                    username = (findProperty("gpr.user") as String?)
                        ?: System.getenv("USERNAME")
                    password = (findProperty("gpr.key") as String?)
                        ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}