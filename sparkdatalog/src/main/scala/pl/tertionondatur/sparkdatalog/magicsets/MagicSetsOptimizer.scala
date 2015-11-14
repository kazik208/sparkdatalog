package pl.tertionondatur.sparkdatalog.magicsets

import java.io.InputStream

import org.apache.commons.io.IOUtils
import org.deri.iris.compiler._
import org.deri.iris.optimisations.magicsets.MagicSets
import pl.appsilon.marek.sparkdatalog.astbuilder.Parser
import socialite.{Yylex, PrettyPrinter}

object MagicSetsOptimizer {

  val magicSets: MagicSets = new MagicSets()
  val irisParser: Parser = new Parser()

  def optimize(sparkDatalogProgram: String): OptimizationResult = {
    val stream: InputStream = IOUtils.toInputStream(sparkDatalogProgram, "UTF-8")
    val irisProgram: String = PrettyPrinter.print(Parser.parse(new Yylex(stream)))
    irisParser.parse(irisProgram)
    val queries = irisParser.getQueries
    if (queries.isEmpty) throw new IllegalArgumentException("Could not find query in provided program")
    OptimizationResultFactory.gen(magicSets.optimise(irisParser.getRules, queries.get(0)))
  }

}
