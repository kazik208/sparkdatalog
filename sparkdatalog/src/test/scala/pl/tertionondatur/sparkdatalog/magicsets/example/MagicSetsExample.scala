package pl.appsilon.marek.sparkdatalog.eval

import org.scalatest._
import pl.appsilon.marek.sparkdatalog.util.SparkTestUtils
import pl.appsilon.marek.sparkdatalog.{Relation}
import pl.tertionondatur.sparkdatalog.magicsets.{DatalogFact, MagicSetsOptimizer}
import pl.tertionondatur.sparkdatalog.magicsets.example.Samples

class MagicSetsExample extends SparkTestUtils with Matchers {

  sparkTest("correctly compute rsg with magic sets optimization") {
    val optimizationResult = MagicSetsOptimizer.optimize(Samples.program)
    val facts : Seq[DatalogFact] = optimizationResult.facts
    val fact1 : DatalogFact = facts.head

    val newdb = Samples.database(sc).include(Relation.unary(fact1.symbol, sc.parallelize(fact1.variables)))
    val optres = optimizationResult.rules.mkString("\n") + "\n" + optimizationResult.query
    val result = newdb.datalog(optres)
    result.collect() should contain ("Q" -> Set(Seq(14), Seq(15)))
  }

}
