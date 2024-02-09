plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.mobil"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mobil"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Navigation Component için ek bağımlılıklar
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha10")

    // Compose dependencies
    implementation("androidx.compose.ui:ui:1.1.0-beta01")
    implementation("androidx.compose.ui:ui-graphics:1.1.0-beta01")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.0-beta01")
    implementation("androidx.compose.material3:material3:1.1.0-beta01")
    implementation("androidx.compose.ui:ui-text-android:1.6.0")
    implementation("androidx.compose.foundation:foundation:1.1.0-beta01")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.0-beta01")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.0-beta01")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.0-beta01")
}
