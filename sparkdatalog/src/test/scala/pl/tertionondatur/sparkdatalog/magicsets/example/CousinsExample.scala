package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext
import org.scalatest._
import pl.appsilon.marek.sparkdatalog.util.SparkTestUtils
import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.tertionondatur.sparkdatalog.magicsets.{DatalogFact, MagicSetsOptimizer}
import pl.tertionondatur.sparkdatalog.magicsets.example.Samples

class CousinsExample extends SparkTestUtils with Matchers {

  sparkTest("correctly compute cousins with magic sets optimization") {

    def database(sc: SparkContext): Database = {
      val edges = sc.parallelize(
        Seq(
          (1, 5),
          (1, 6),
          (2, 5),
          (6,13),
          (7,14),
          (8,14),
          (15,13),
          (14,15)
        )
      )

      Database(Relation.binary("Par", edges))
    }

    def program: String =
      """
        |Sg(x,y) :- Par(x,z), Par(y,z).
        |Sg(x,y) :- Par(x,xp), Par(y,yp), Sg(xp,yp).
        |Q(x) :- Sg(1,x).
      """.stripMargin

    val optimizationResult = MagicSetsOptimizer.optimize(program)
    val facts : Seq[DatalogFact] = optimizationResult.facts
    val fact1 : DatalogFact = facts.head

    val newdb = database(sc).include(Relation.unary(fact1.symbol, sc.parallelize(fact1.variables)))
    val optres = optimizationResult.rules.mkString("\n") + "\n" + optimizationResult.query
    val result = newdb.datalog(optres)
    result.collect() should contain ("Q" -> Set(Seq(1), Seq(2), Seq(14)))
  }

}

