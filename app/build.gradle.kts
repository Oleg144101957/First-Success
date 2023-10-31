plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {

    signingConfigs {
        create("release") {
            if (project.hasProperty("MYAPP_RELEASE_STORE_FILE")) {
                storeFile = file(project.findProperty("MYAPP_RELEASE_STORE_FILE"))
                storePassword = project.findProperty("MYAPP_RELEASE_STORE_PASSWORD") as String
                keyAlias = project.findProperty("MYAPP_RELEASE_KEY_ALIAS") as String
                keyPassword = project.findProperty("MYAPP_RELEASE_KEY_PASSWORD") as String
            }
        }
    }


    namespace = "ua.playappsuccess.first"
    compileSdk = 34

    defaultConfig {
        applicationId = "ua.playappsuccess.first"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}