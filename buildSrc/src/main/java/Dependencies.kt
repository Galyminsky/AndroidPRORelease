import org.gradle.api.JavaVersion

object Config {
    const val compile_sdk = 31
    const val application_id = "com.example.lightdictionary"
    const val min_sdk = 23
    const val target_sdk = 31
    val java_version = JavaVersion.VERSION_1_8
    const val jvm_target = "1.8"
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Versions {
    const val room = "2.3.0"
    const val coil = "1.4.0"
    const val koin = "3.1.3"

    const val retrofit = "2.9.0"
    const val retrofit_coroutines = "0.9.2"

    const val view_binding = "1.5.2"

    const val ktx_core = "1.7.0"
    const val ktx_fragment = "1.4.0"

    const val app_compat = "1.4.0"
    const val material = "1.4.0"
    const val constraint = "2.1.2"
    const val swipe_refresh = "1.1.0"

    const val junit = "4.+"
    const val junit_ext = "1.1.3"
    const val espresso = "3.4.0"
}