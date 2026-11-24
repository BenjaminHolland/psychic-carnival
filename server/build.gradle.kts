
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinx.rpc.grpc)
}


application {
    mainClass = "io.ktor.server.netty.EngineMain"
}
//rpc {
//    protoc()
//}

kotlin {
    jvmToolchain(21)
}
dependencies {
    //  implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    //implementation("io.grpc:grpc-protobuf:1.68.1")
    //implementation("com.google.protobuf:protobuf-kotlin:4.28.2")
    implementation(libs.kotlinx.rpc.grpc.protobuf)
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.di)
    implementation(ktorLibs.server.metrics)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.openapi)
    implementation(ktorLibs.server.routingOpenapi)
    implementation(ktorLibs.server.swagger)
    implementation(libs.grpc.netty)
    implementation(libs.kotlinx.rpc.grpc.ktorServer)
    implementation(libs.logback.classic)
    implementation(project(":core"))

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
    testImplementation(libs.kotlinx.rpc.grpc.client)
}
