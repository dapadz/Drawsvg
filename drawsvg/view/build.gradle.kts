plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}
android {
    namespace = "com.dapadz.drawsvg.view"
    compileSdk = 36
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
    kotlinOptions { jvmTarget = "11" }
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
    api(project(":drawsvg:core"))
}
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("Release") {
                groupId = "com.github.dapadz"
                artifactId = "drawsvg-view"
                version = "0.0.6"
                from(components["release"])
                pom {
                    name.set("drawsvg-view")
                    description.set("Android view layer for drawsvg")
                }
            }
        }
    }
}