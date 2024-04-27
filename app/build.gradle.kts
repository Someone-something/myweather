import org.apache.tools.ant.util.JavaEnvUtils.VERSION_11

plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.gaofh.myweather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gaofh.myweather"
        minSdk = 31
        targetSdk = 34
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
    implementation(files("libs\\gson-2.10.1.jar"))
    implementation("com.squareup.okhttp3:okhttp:3.4.1")
    implementation("com.github.bumptech.glide:glide:3.7.0")
    implementation(files("libs\\litepal-1.3.2.jar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}