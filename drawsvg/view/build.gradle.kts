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
    api("com.github.dapadz:drawsvg-core:0.0.5")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.dapadz"
            artifactId = "drawsvg-view"
            version = "0.0.5"
            from(components["release"])
            pom {
                name.set("drawsvg-view")
            }
        }
    }
    repositories {
        mavenLocal()
    }
}
