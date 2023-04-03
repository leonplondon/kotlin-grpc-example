package pro.darkgod.application.outbound

import pro.darkgod.domain.Ping
import pro.darkgod.domain.Pong

interface PingTwoService {
  fun ping(ping: Ping): Pong
}
