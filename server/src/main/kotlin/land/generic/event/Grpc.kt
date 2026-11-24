package land.generic.event

import io.ktor.server.application.Application
import kotlinx.rpc.grpc.ktor.server.grpc
import kotlinx.rpc.registerService
import land.generic.GRPC_PORT
import land.generic.calendar.v1.EventService
import land.generic.calendar.v1.SampleService

fun Application.configureGrpc() {
    grpc(GRPC_PORT) {
        services {
            registerService<SampleService> { SampleServiceImpl() }
            registerService<EventService> { EventServiceImpl() }
        }
    }
}
