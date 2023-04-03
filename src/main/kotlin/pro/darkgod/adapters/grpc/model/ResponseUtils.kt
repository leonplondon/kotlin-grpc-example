package pro.darkgod.adapters.grpc.model

import io.grpc.stub.StreamObserver

fun <R> StreamObserver<R>?.unaryResponse(response: R) {
  this?.run {
    onNext(response)
    onCompleted()
  }
}
