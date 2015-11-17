package pl.tertionondatur.sparkdatalog.magicsets

import org.deri.iris.api.IProgramOptimisation._
import org.deri.iris.api.basics.{IQuery, IRule}
import scala.collection.JavaConversions._
import pl.appsilon.marek.sparkdatalog.Relation

object OptimizationResultFactory {

  def gen(optimizationResult: Result) = {
    val rules = optimizationResult.rules.iterator().toSeq
    val sparkDatalogRules = rules.filter(!_.getBody.isEmpty).map(_.toString)
    val sparkDatalogFacts = rules.filter(_.getBody.isEmpty).map(toFact)
    new OptimizationResult(sparkDatalogFacts, sparkDatalogRules, toSparkDatalog(optimizationResult.query))
  }

  def toFact(rule: IRule): DatalogFact = {
    val atom = rule.getHead.get(0).getAtom
    val symbol = atom.getPredicate.getPredicateSymbol
    val variables = atom.getTuple.iterator().toSeq.map(_.toString.toInt)
    new DatalogFact(symbol.charAt(0).toUpper + symbol.substring(1), variables)
  }

  def toSparkDatalog(query: IQuery): String = {
    query.toString.replace("?-", "Q(x) :- ")
  }
}
