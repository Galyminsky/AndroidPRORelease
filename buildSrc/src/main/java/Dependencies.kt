import org.gradle.api.JavaVersion

object Config {
    const val compile_sdk = 33
    const val application_id = "com.example.lightdictionary"
    const val min_sdk = 23
    const val target_sdk = 33
    val java_version = JavaVersion.VERSION_1_8
    const val jvm_target = "1.8"
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Versions {
    const val room = "2.5.1"
    const val coil = "2.4.0"
    const val koin = "3.4.0"

    const val retrofit = "2.9.0"
    const val retrofit_coroutines = "0.9.2"

    const val ktx_core = "1.8.0"
    const val ktx_fragment = "1.5.7"
    const val gson = "2.10.1"

    const val app_compat = "1.6.1"
    const val material = "1.10"
    const val constraint = "2.1.2"
    const val swipe_refresh = "1.1.0"

    const val junit = "4.12"
    const val junit_ext = "1.1.3"
    const val espresso = "3.4.0"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
}

object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val coroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit_coroutines}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.ktx_core}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.ktx_fragment}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}

object Ui {
    const val swipe_refresh_layout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh}"
    const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Testing {
    const val junit = "junit:junit:${Versions.junit}"
    const val junit_ext = "androidx.test.ext:junit:${Versions.junit_ext}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object Modules {
    const val app = ":app"
    const val model = ":model"
    const val repository = ":repository"
}