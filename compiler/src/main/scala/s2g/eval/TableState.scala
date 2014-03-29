package s2g.eval

import s2g.ast.value.{ValueLiteral, ValueVar, Value}

case class TableState(instances: Set[Instance] = Set()) {

  def add(instance: Instance): TableState = TableState(instances + instance)

  def contains(instance: Instance): Boolean = instances contains instance

  def findMatching(pattern: Pattern): Set[PartialSolution] =
    instances flatMap { instance => matchInstance(pattern, instance) }

  private def mergeSolutionOptions(accumulated: Option[PartialSolution], next: Option[PartialSolution]): Option[PartialSolution] =
    (accumulated, next) match {
      case (Some(accumulated), Some(partialSolution)) => Some(accumulated ++ partialSolution)
      case _ => None
    }

  def matchInstance(pattern: Pattern, instance: Instance): Option[PartialSolution] = {
    val matches: Seq[Option[PartialSolution]] = (pattern.values zip instance.values) map { case (patternValue, instanceValue) =>
      matchPosition (patternValue, instanceValue) }
    val accumulated: Option[PartialSolution] = Some(PartialSolution())
    matches.foldLeft(accumulated)(mergeSolutionOptions(_, _))
  }

  def matchPosition(patternValue: Value, instanceValue: ValueLiteral): Option[PartialSolution] = patternValue match {
    case `instanceValue` => Some(PartialSolution())
    case ValueVar(name) => Some(PartialSolution(Map(name -> instanceValue)))
    case _ => None
  }

  def toStringInstances(prefix: String): String = (instances map (prefix + _.toString)).mkString("\n")
}
