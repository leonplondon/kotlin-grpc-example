package pro.darkgod.adapters.grpc.server

import io.grpc.health.v1.HealthCheckResponse.ServingStatus
import io.grpc.stub.StreamObserver
import pro.darkgod.config.HealthChecker
import pro.darkgod.proto.commons.EmptyMessage
import pro.darkgod.proto.commons.EmptyServiceGrpc.EmptyServiceImplBase
import pro.darkgod.proto.commons.EmptyServiceGrpc.getTestCallMethod
import pro.darkgod.proto.commons.EmptyServiceGrpc.getTestStreamingCallMethod
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class EmptyServiceImpl(healthChecker: HealthChecker) : EmptyServiceImplBase() {

  init {
    healthChecker.setStatus(getTestCallMethod().fullMethodName, ServingStatus.SERVING)
    healthChecker.setStatus(getTestStreamingCallMethod().fullMethodName, ServingStatus.SERVING)
  }

  override fun testCall(request: EmptyMessage?, responseObserver: StreamObserver<EmptyMessage>?) {
    responseObserver?.run {
      onNext(request)
      onCompleted()
    }
  }

  override fun testStreamingCall(
    request: EmptyMessage?,
    responseObserver: StreamObserver<EmptyMessage>?
  ) {
    val randomTimes = Random.nextInt(1, 11)

    repeat(randomTimes) {
      pushMessage(
        request = request,
        responseObserver = responseObserver,
      )
    }
    responseObserver?.onCompleted()
  }

  private fun pushMessage(request: EmptyMessage?, responseObserver: StreamObserver<EmptyMessage>?) {
    Thread.sleep(TimeUnit.SECONDS.toMillis(1))
    responseObserver?.onNext(request)
  }
}
