import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.cookandroid.jlptvocabularyapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cookandroid.jlptvocabularyapplication"
        minSdk = 21


        // TODO: 마지막에 34로 업그레이드

        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
        buildConfigField("String", "weather_api_key", getWeatherApiKey("weather_api_key"))
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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
}

fun getWeatherApiKey(propertyKey : String) : String{
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //for json parsing
    implementation ("com.google.code.gson:gson:2.9.0")

    //for api
    implementation("com.squareup.retrofit2:retrofit:2.6.4")
    implementation("com.squareup.retrofit2:converter-gson:2.6.4")
    implementation("com.squareup.retrofit2:converter-scalars:2.6.4")

    //for gps
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    //for splash screen
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // for Room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

}