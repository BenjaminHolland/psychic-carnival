import kotlinx.rpc.protoc.proto

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.rpc.grpc)
    alias(libs.plugins.kotlin.serialization)
}


rpc {
    protoc{
        // use Buf to generate protos
        buf {
            deps {
                // Pulls google/api/annotations.proto and google/api/http.proto
                module("buf.build/googleapis/googleapis")
                // Pulls gnostic/openapi/v3/annotations.proto
                module("buf.build/gnostic/gnostic")
            }
        }
        plugins {
            // add the opeapi v3 generator plugin.
            create("openapi") {
                remote {
                    locator = "buf.build/grpc-ecosystem/openapiv3"
                }
//                options.put("generate_unbound_methods", "true")
            }
        }
    }
}

kotlin {
    jvm()


    sourceSets {
        commonMain{
            // use the openapi plugin.
            proto {
                plugin {
                    getByName("openapi")
                }
            }
            dependencies {
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

/**
 * Add the generated openapi definition to the output resources.
 */
tasks.named<ProcessResources>("jvmProcessResources") {
    // before processing resources, make sure that the proto generator runs.
    dependsOn("bufGenerateCommonMain")
    // shove the output from the generate files into the jar.
    from(layout.buildDirectory.dir("protoBuild/generated/commonMain/openapi")){
        into("openapi")
    }
}