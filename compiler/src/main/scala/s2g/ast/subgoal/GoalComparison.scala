package s2g.ast.subgoal

import s2g.eval.{EvaluationState, PartialSolution}
import s2g.ast.exp.Exp
import s2g.ast.comparisonoperator.ComparisonOperator

case class GoalComparison(left: Exp, right: Exp, operator: ComparisonOperator) extends Subgoal {
  override def solveOn(partialSolution: PartialSolution, evaluationState: EvaluationState): Set[PartialSolution] = {
    val leftValue = left.evaluate(partialSolution)
    val rightValue = right.evaluate(partialSolution)
    val comparison = leftValue.typ.compare(leftValue, rightValue)
    if (operator.decide(comparison)) {
      Set(partialSolution)
    } else {
      Set()
    }
  }
}
