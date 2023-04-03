package pro.darkgod.adapters.grpc.server

import io.grpc.health.v1.HealthCheckResponse.ServingStatus
import io.grpc.stub.StreamObserver
import pro.darkgod.adapters.grpc.model.createdNow
import pro.darkgod.adapters.grpc.model.unaryResponse
import pro.darkgod.config.Constants.SERVICE_ONE_ID
import pro.darkgod.config.HealthChecker
import pro.darkgod.config.logMessage
import pro.darkgod.proto.ServiceOneGrpc
import pro.darkgod.proto.commons.Ping
import pro.darkgod.proto.commons.Pong

val logSO = logMessage(SERVICE_ONE_ID)

class ServiceOneAdapter(healthChecker: HealthChecker) : ServiceOneGrpc.ServiceOneImplBase() {

  init {
    healthChecker.setStatus(
      ServiceOneGrpc.getPingMethod().fullMethodName,
      ServingStatus.SERVING,
    )
  }

  override fun ping(request: Ping?, responseObserver: StreamObserver<Pong>?) {
    logSO("Ping request")

    val pong = Pong.newBuilder()
      .createdNow()
      .setService(SERVICE_ONE_ID)
      .build()

    responseObserver.unaryResponse(pong)
  }
}
