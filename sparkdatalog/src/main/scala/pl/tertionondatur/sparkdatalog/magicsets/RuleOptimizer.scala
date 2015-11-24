package pl.tertionondatur.sparkdatalog.magicsets

import org.deri.iris.api.basics.{ILiteral}
import org.deri.iris.optimisations.magicsets.AdornedProgram.AdornedPredicate
import collection.JavaConversions._
import collection.JavaConverters._
import java.util.List

object RuleOptimizer {

    def reorder(adornedPredicate: AdornedPredicate, literal: ILiteral, literals: List[ILiteral]): List[ILiteral] = {
      val boundVars = boundVarsFrom(adornedPredicate, literal)
      reorder(boundVars, literals).asJava
    }

    def reorder(boundVars: Set[String], literals: Seq[ILiteral], acc: Seq[ILiteral] = Seq()): Seq[ILiteral] = {
      val freeVarCounts = literals.map(freeVars(boundVars))
      val orderedLiterals = (freeVarCounts zip literals).sortBy(_._1).map(_._2)
      if (orderedLiterals.isEmpty) {
        return acc
      }
      val first = orderedLiterals.head
      val tail = orderedLiterals.tail
      val newVars = varsFrom(first).toSet
      reorder(boundVars ++ newVars, tail, acc :+ first)
    }

    private def freeVars(inputVars: Set[String])(literal: ILiteral) = {
      val vars = varsFrom(literal)
      vars.count(!inputVars.contains(_))
    }

    private def varsFrom(literal: ILiteral): Seq[String] = {
      literal.getAtom.getTuple.getAllVariables.map(_.getValue)
    }

    private def boundVarsFrom(adornedPredicate: AdornedPredicate, literal: ILiteral): Set[String] = {
      val boundFlags = adornedPredicate.getAdornment.map(_.representation == "b")
      val vars = varsFrom(literal)
      (boundFlags zip vars).filter(_._1).map(_._2).toSet
    }
}