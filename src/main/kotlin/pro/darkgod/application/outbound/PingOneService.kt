package pro.darkgod.application.outbound

import pro.darkgod.domain.Ping
import pro.darkgod.domain.Pong

interface PingOneService {
  fun ping(ping: Ping): Pong
}
