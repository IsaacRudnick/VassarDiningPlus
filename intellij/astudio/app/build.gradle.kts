plugins {
    id("com.android.application")
}

android {
    namespace = "edu.vassar.cmpu203.app"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "edu.vassar.cmpu203.app"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(files("libs/json-simple-1.1.1.jar"))
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    // testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
   // androidTestImplementation("androidx.test.ext:junit:1.1.5")
   // androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}