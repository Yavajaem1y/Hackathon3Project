plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.androidlesson.hackathon3project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.androidlesson.hackathon3project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    viewBinding {
        enable = true
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


dependencies {

    //Modules
    implementation(project(":data"))
    implementation(project(":domain"))

    //Dagger
    implementation("com.google.dagger:dagger:2.52")
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    annotationProcessor("com.google.dagger:dagger-compiler:2.52")

    implementation ("com.google.android.exoplayer:exoplayer-core:2.17.0")
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.17.0")

    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.tbuonomo:dotsindicator:4.3")


    //Fragment navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation ("androidx.navigation:navigation-ui-ktx:2.6.0")

    //RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")

    //LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")

    //CircleImage
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}