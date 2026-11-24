package land.generic

import kotlinx.rpc.grpc.client.GrpcClient
import kotlinx.rpc.withService
import land.generic.calendar.v1.ClientGreeting
import land.generic.calendar.v1.SampleService

suspend fun main() {
    val client = GrpcClient("localhost", GRPC_PORT) {
        credentials = plaintext()
    }

    val service = client.withService<SampleService>()

    val response = service.greeting(
        object:ClientGreeting {
            override val name: String
                get() = "World"
        }
    )
    println("Response: ${response.content}")

    client.shutdown()
    client.awaitTermination()
}
