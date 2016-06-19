package io.sandbox.gatling

import scala.concurrent.duration.DurationInt

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MySimulation extends Simulation {

  val feeder = ssv("input.txt").random

  val sc = scenario("Basic request scenario")
            .feed(feeder)
            .exec(
              http("Timeout")
              .get("/${status_code}"))

  def conf = http.baseURL("https://http.cat")

  val concurrentUsers = Integer.getInteger("concurrentUsers", 1)

  setUp(sc.inject(constantUsersPerSec(concurrentUsers toDouble).during(60 seconds))).protocols(conf)

}
