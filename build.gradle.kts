
plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinx.rpc.grpc) apply false
}


tasks.matching { it.name.startsWith("generateBufGenYaml") }.configureEach {
    doLast {
        outputs.files.filter { it.name == "buf.gen.yaml" && it.exists() }.forEach { file ->
            val cleaned = file.readLines()
                .filterNot { it.contains("explicitApiModeEnabled") }
                .joinToString("\n")
            file.writeText(cleaned)
        }
    }
}