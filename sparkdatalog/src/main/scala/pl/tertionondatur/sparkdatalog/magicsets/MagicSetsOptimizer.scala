package pl.tertionondatur.sparkdatalog.magicsets

import org.deri.iris.compiler._
import org.deri.iris.optimisations.magicsets.MagicSets

object MagicSetsOptimizer {

  val magicSets: MagicSets = new MagicSets()
  val irisParser: Parser = new Parser()

  def optimize(program: String): String = {
    irisParser.parse(program)
    val optimizationResult = magicSets.optimise(irisParser.getRules, irisParser.getQueries.get(0))
    optimizationResult.rules.toString + "\n" + optimizationResult.query.toString
  }

}
