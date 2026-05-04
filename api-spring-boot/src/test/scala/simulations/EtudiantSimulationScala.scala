package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
 * Simulation Gatling en Scala pour tests de stress
 * Alternative à la version Java
 */
class EtudiantSimulationScala extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val scn = scenario("Liste des étudiants")
    .exec(
      http("GET /api/etudiants")
        .get("/api/etudiants")
        .check(status.is(200))
    )

  setUp(
    scn.inject(rampUsers(50).during(30.seconds))
  ).protocols(httpProtocol)
   .assertions(
     global.responseTime.max.lt(5000),
     global.responseTime.mean.lt(1000),
     global.successfulRequests.percent.gt(95)
   )
}
