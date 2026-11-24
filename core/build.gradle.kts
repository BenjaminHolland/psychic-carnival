import kotlinx.rpc.protoc.proto

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.rpc.grpc)
    alias(libs.plugins.kotlin.serialization)
}


rpc {
    protoc{
        plugins {
            // add the opeapi v3 generator plugin.
            create("openapi") {
                    local {
                        // Point directly to the executable (e.g., ~/go/bin/protoc-gen-openapi)
                        executor(rootProject.file("third_party/tools/bin/protoc-gen-openapi").absolutePath)
                    }
            }
        }
    }
}

kotlin {
    jvm()
//    linuxX64("linux") {
//        binaries {
//            sharedLib {
//                // Optional: sets the output filename to libmylib.so (defaults to project name)
//                baseName = "event.core"
//            }
//        }
//    }
    sourceSets {
        commonMain{
            // use the openapi plugin.
            proto {
                fileImports.from(
                    rootProject.file("third_party/proto/googleapis"))
                plugin {
                    getByName("openapi")
                }
            }
            resources.srcDir(files(layout.buildDirectory.dir("protoBuild/generated/commonMain")) {
                builtBy("bufGenerateCommonMain")
            })

            dependencies {
              //  implementation("io.grpc:grpc-kotlin-stub:1.4.1")
                //implementation("io.grpc:grpc-protobuf:1.68.1")
               // implementation("com.google.protobuf:protobuf-kotlin:4.28.2")
                implementation(libs.kotlinx.rpc.grpc.core)
                implementation(libs.kotlinx.rpc.grpc.protobuf)
            }
        }
    }
}



// fix a dumb bug where the rpc plugin injects explicitApiModeEnabled when that doesn't exist.
tasks.matching { it.name.startsWith("generateBufGenYamlCommon") }.configureEach {
    doLast {
        val file = outputs.files.single { it.name == "buf.gen.yaml" }
        file.writeText(file.readLines().filterNot { "explicitApiModeEnabled=" in it || "platform=" in it }.joinToString("\n"))
    }
}
//
///**
// * Add the generated openapi definition to the output resources.
// */
//tasks.named<ProcessResources>("jvmProcessResources") {
//    // before processing resources, make sure that the proto generator runs.
//    dependsOn("bufGenerateCommonMain")
//    // shove the output from the generate files into the jar.
//    from(layout.buildDirectory.dir("protoBuild/generated/commonMain/openapi")){
//        into("openapi")
//    }
//}