import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystorePropertiesFile = rootProject.file("keystore.properties")
        if (keystorePropertiesFile.exists()) {
            val keystoreProperties = Properties()
            keystoreProperties.load(keystorePropertiesFile.inputStream())
            val sha1Fingerprint = keystoreProperties["SHA1_FINGERPRINT"] as String
            println("SHA-1 Fingerprint: $sha1Fingerprint") // Print for verification (remove in production)
            // Use the sha1Fingerprint variable as needed (with caution)
        } else {
            println("Warning: keystore.properties not found. SHA-1 fingerprint not loaded.")
        }
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
    implementation(libs.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.runtime)
    implementation(libs.material)
    implementation(libs.androidx.appcompat) {
        exclude(group = "androidx.appcompat", module = "appcompat")
    }
    implementation(libs.androidx.fragment)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.gif.drawable)
    implementation(libs.androidx.viewpager2)
    implementation(libs.firebase.auth)
    implementation(libs.google.firebase.database)
    implementation(libs.firebase.inappmessaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.firebase.messaging)
    implementation(libs.places)


}
