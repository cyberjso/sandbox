package io.sandbox.gatling

import scala.concurrent.duration.DurationInt

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MySimulation extends Simulation {

  val sc = scenario("Timeout request scenario").exec(http("Timeout").get("/408"))

  def conf = http.baseURL("https://http.cat")

  setUp(sc.inject(constantUsersPerSec(1).during(60 seconds))).protocols(conf)

}
