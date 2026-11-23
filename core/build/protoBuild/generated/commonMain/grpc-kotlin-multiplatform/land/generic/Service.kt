@file:OptIn(InternalRpcApi::class)

package land.generic

import kotlinx.rpc.grpc.annotations.Grpc
import kotlinx.rpc.internal.utils.InternalRpcApi

@Grpc
interface SampleService {
    suspend fun greeting(message: ClientGreeting): ServerGreeting
}
