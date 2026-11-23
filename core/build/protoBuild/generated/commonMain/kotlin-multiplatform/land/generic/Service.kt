@file:OptIn(InternalRpcApi::class)
@file:Suppress("ClassName")

package land.generic

import kotlinx.rpc.internal.utils.InternalRpcApi
import kotlinx.rpc.protobuf.internal.GeneratedProtoMessage

@GeneratedProtoMessage
interface ClientGreeting {
    val name: String
}

@GeneratedProtoMessage
interface ServerGreeting {
    val content: String
}
