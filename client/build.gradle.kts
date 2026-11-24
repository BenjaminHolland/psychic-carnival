
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.rpc.grpc)
}


rpc {
    protoc()
}

kotlin {
    jvm()
//    iosArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(ktorLibs.client.core)
            implementation(libs.kotlinx.rpc.grpc.client)
            implementation(project(":core"))
        }

    }
}
