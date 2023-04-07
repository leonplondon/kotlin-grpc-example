package pro.darkgod.adapters.grpc.server

import io.grpc.health.v1.HealthCheckResponse
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import pro.darkgod.adapters.grpc.model.createdNow
import pro.darkgod.adapters.grpc.model.unaryResponse
import pro.darkgod.config.Constants.SERVICE_TWO_ID
import pro.darkgod.config.HealthChecker
import pro.darkgod.proto.ServiceTwoGrpc
import pro.darkgod.proto.commons.Ping
import pro.darkgod.proto.commons.Pong

class ServiceTwoAdapter(healthChecker: HealthChecker) : ServiceTwoGrpc.ServiceTwoImplBase() {

  init {
    healthChecker.setStatus(
      ServiceTwoGrpc.getPingMethod().fullMethodName,
      HealthCheckResponse.ServingStatus.SERVING,
    )
  }

  private val logger = LoggerFactory.getLogger(ServiceTwoAdapter::class.java)

  override fun ping(request: Ping?, responseObserver: StreamObserver<Pong>?) {
    logger.info("Ping request")

    val pong = Pong.newBuilder()
      .createdNow()
      .setService(SERVICE_TWO_ID)
      .build()

    responseObserver.unaryResponse(pong)
  }

}
