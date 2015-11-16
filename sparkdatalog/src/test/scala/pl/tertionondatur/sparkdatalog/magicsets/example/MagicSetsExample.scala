package pl.appsilon.marek.sparkdatalog.eval

import org.scalatest._
import pl.appsilon.marek.sparkdatalog.util.SparkTestUtils
import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.tertionondatur.sparkdatalog.magicsets.{DatalogFact, MagicSetsOptimizer}
import pl.tertionondatur.sparkdatalog.magicsets.example.Samples

class MagicSetsExample extends SparkTestUtils with Matchers {

  sparkTest("correctly compute rsg with magic sets optimization") {
    val optimizationResult = MagicSetsOptimizer.optimize(Samples.program)
    val facts : Seq[DatalogFact] = optimizationResult.facts
    val fact1 : DatalogFact = facts.head

    val ups = sc.parallelize(Seq((1, 5), (1, 6), (6,13), (7,14), (8,14), (9,15), (10,15)))
    val flats = sc.parallelize(Seq((7, 6), (13,14),(13,15),(16,13)))
    val downs = sc.parallelize(Seq((12,6),(13,6),(7,2),(8,3),(9,4)))

    val db = Database(Relation.binary("Up", ups), Relation.binary("Flat", flats), Relation.binary("Down", downs))
    val newdb = db.include(Relation.unary(fact1.symbol, sc.parallelize(fact1.variables)))
    // val optimizedDatabase = Samples.database.addFacts(optimizationResult.facts)
    val optres = optimizationResult.rules.mkString("\n") // + "\n" + optimizationResult.query
    val result = newdb.datalog(optres)
    result.collect() should contain ("Rsg" -> Set(Seq(2, 1)))
  }

}
