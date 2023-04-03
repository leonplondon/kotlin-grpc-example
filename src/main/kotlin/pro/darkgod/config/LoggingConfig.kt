package pro.darkgod.config

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor

val loggingInterceptor = object : ServerInterceptor {
  override fun <ReqT : Any?, RespT : Any?> interceptCall(
    call: ServerCall<ReqT, RespT>?,
    headers: Metadata?,
    next: ServerCallHandler<ReqT, RespT>?
  ): ServerCall.Listener<ReqT> {
    call?.run {
      println("New request to service ${this.methodDescriptor.fullMethodName}")
    }

    return next?.let { next.startCall(call, headers) }
      ?: throw RuntimeException("Unable to forward call")
  }
}

fun logMessage(loggerId: String): (msg: String) -> Unit = { msg ->
  println("[$loggerId]: $msg")
}
