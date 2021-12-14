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