package pro.darkgod.adapters.grpc.model

import com.google.protobuf.timestamp
import pro.darkgod.domain.Pong
import java.time.Instant

fun pro.darkgod.proto.commons.Pong.toDomain(): Pong = Pong(
  service = this.service,
  timestamp = Instant.ofEpochSecond(this.timestamp.seconds, this.timestamp.nanos.toLong())
)

fun pro.darkgod.proto.commons.Pong.Builder.createdNow() = apply {
  val timestamp = timestamp {
    val now = Instant.now()
    seconds = now.epochSecond
    nanos = now.nano
  }

  this.setTimestamp(timestamp)
}
