package pro.darkgod.adapters.grpc.model

import pro.darkgod.domain.Ping

fun from(ping: Ping): pro.darkgod.proto.commons.Ping {
  return pro.darkgod.proto.commons.Ping.newBuilder()
    .setCaller(ping.caller)
    .build()
}
