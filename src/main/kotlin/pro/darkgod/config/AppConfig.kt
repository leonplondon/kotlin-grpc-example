package pro.darkgod.config

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.ServerInterceptors
import kotlin.concurrent.thread

fun ServerBuilder<*>.bindAppServices(adapters: List<BindableService>): ServerBuilder<*> = apply {
  adapters.map { service -> ServerInterceptors.intercept(service, loggingInterceptor) }
    .forEach { addService(it) }
}

fun Server.attachShutdownHook(healthChecker: HealthChecker) {
  val shutdownThread = thread(start = false) {
    healthChecker.shutdown()
    shutdown()
  }
  Runtime.getRuntime()
    .addShutdownHook(shutdownThread)
}
