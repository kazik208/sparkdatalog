package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext
import org.scalatest._
import pl.appsilon.marek.sparkdatalog.util.SparkTestUtils
import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.tertionondatur.sparkdatalog.magicsets.{DatalogFact, MagicSetsOptimizer}
import pl.tertionondatur.sparkdatalog.magicsets.example.Samples

class TransitiveClosureExample extends SparkTestUtils with Matchers {

  sparkTest("correctly compute transitive closure with magic sets optimization") {

    def database(sc: SparkContext): Database = {
      val edges = sc.parallelize(
        Seq(
          (1, 5),
          (1, 6),
          (6,13),
          (7,14),
          (8,14),
          (13,15),
          (10,15)
        )
      )

      Database(Relation.binary("E", edges))
    }

    def program1: String =
      """
        |S(x,y) :- E(x,y).
        |S(x,y) :- E(x,z),S(z,y).
        |Q(x) :- S(1,x).
      """.stripMargin

    def program2: String =
      """
        |S(x,y) :- E(x,y).
        |S(x,y) :- S(x,z),S(z,y).
        |Q(x) :- S(1,x).
      """.stripMargin

    for ( program <- Array(program1, program2)) {
      val optimizationResult = MagicSetsOptimizer.optimize(program)
      val facts : Seq[DatalogFact] = optimizationResult.facts
      val fact1 : DatalogFact = facts.head

      val newdb = database(sc).include(Relation.unary(fact1.symbol, sc.parallelize(fact1.variables)))
      val optres = optimizationResult.rules.mkString("\n") + "\n" + optimizationResult.query
      val result = newdb.datalog(optres)
      result.collect() should contain ("Q" -> Set(Seq(5), Seq(6), Seq(13), Seq(15)))
    }
  }

}

