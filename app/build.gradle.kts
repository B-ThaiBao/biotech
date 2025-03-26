plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.hcmut.assignment.biotech"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hcmut.assignment.biotech"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // https://developer.android.com/media/camera/camerax/architecture
    val cameraxVersion = "1.5.0-alpha03"
    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")

    // Camerax View class
    implementation("androidx.camera:camera-view:${cameraxVersion}")

    // Gson: https://github.com/google/gson
    val gsonVersion = "2.12.1"
    implementation("com.google.code.gson:gson:${gsonVersion}")

    // https://developer.android.com/jetpack/androidx/releases/recyclerview
    val recyclerViewVersion = "1.2.1"
    implementation("androidx.recyclerview:recyclerview:${recyclerViewVersion}")
}