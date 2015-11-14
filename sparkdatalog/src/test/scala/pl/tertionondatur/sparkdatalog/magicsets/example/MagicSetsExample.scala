package pl.appsilon.marek.sparkdatalog.eval

import org.scalatest._
import pl.appsilon.marek.sparkdatalog.util.SparkTestUtils
import pl.tertionondatur.sparkdatalog.magicsets.MagicSetsOptimizer
import pl.tertionondatur.sparkdatalog.magicsets.example.Samples

class MagicSetsExample extends SparkTestUtils with Matchers {

  sparkTest("correctly compute rsg with magic sets optimization") {
    val optimizationResult = MagicSetsOptimizer.optimize(Samples.program)
    // val optimizedDatabase = Samples.database.addFacts(optimizationResult.facts)
    val result = Samples.database.datalog(optimizationResult.rules.mkString("\n") + "\n" + optimizationResult.query)
    result.collect() should contain ("Q" -> Set(Seq(2, 1)))
  }

}
