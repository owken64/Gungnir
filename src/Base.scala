package com.isageek.yamanogusha.ronri.base

abstract class LogicalFormula {
  def truth: Option[Boolean]
}

case class Proposition(val sentence:String) extends LogicalFormula{
  var t: Option[Boolean] = None
  def truth: Option[Boolean] = t
}

abstract class TruthFunction extends LogicalFormula

abstract class ZeroPlaceTruthFunction extends TruthFunction {
  def semantics() : Boolean
  def truth: Option[Boolean] = Some(semantics())
}

abstract class OnePlaceTruthFunction(arg: LogicalFormula) extends TruthFunction {
  def semantics(arg: Boolean): Boolean
  def truth: Option[Boolean] = {
    if (arg.truth.isDefined) Some(semantics(arg.truth.get))
	else None
  }
}

abstract class TwoPlaceTruthFunction(l: LogicalFormula, r: LogicalFormula) extends TruthFunction {
  def semantics(left: Boolean, right: Boolean): Boolean
  def truth: Option[Boolean] = {
    if (l.truth.isDefined && r.truth.isDefined) Some(semantics(l.truth.get, r.truth.get))
	else None 
  }
}

case class True() extends ZeroPlaceTruthFunction {
  def semantics(): Boolean = true
}

case class False() extends ZeroPlaceTruthFunction {
  def semantics() : Boolean = false
}

case class Not(formula: LogicalFormula) extends OnePlaceTruthFunction(formula) {
  def semantics(arg: Boolean): Boolean = {
    if ( arg == true ) false
	else true
  }
}
    
case class And(left: LogicalFormula, right: LogicalFormula) extends TwoPlaceTruthFunction(left, right) {
  def semantics(left: Boolean, right: Boolean): Boolean = {
    if ( left == true ) {
	  if (right == true) true
	  else false
	}
	else {
	  if (right == true) false
	  else false
	}
  }
}

case class Or(left: LogicalFormula, right: LogicalFormula) extends TwoPlaceTruthFunction(left, right) {
  def semantics(left: Boolean, right: Boolean): Boolean = {
    if ( left == true ) {
	  if (right == true) true
	  else true
	}
	else {
	  if (right == true) true
	  else false
	}
  }
}

case class IfThen(premise: LogicalFormula, consequence: LogicalFormula) extends TwoPlaceTruthFunction(premise, consequence) {
  def semantics(left: Boolean, right: Boolean): Boolean = {
    if ( left == true ) {
	  if (right == true) true
	  else false
	}
	else {
	  if (right == true) true
	  else true
	}
  }
}

case class Iff(left: LogicalFormula, right: LogicalFormula) extends TwoPlaceTruthFunction(left, right) {
  def semantics(left: Boolean, right: Boolean): Boolean = {
    if ( left == true ) {
	  if (right == true) true
	  else false
	}
	else {
	  if (right == true) false
	  else true
	}
  }
}

object PropositionManager {
  private var list: List[Proposition] = List()
  def add(p: Proposition): Unit = {
    if (list contains p) {
	  throw new Exception( p + " has been already defined.")
	}
	else {
	  list = p :: list
	}
  }
  
  def get(p: Proposition):Option[Proposition] = {
    def content[T](list: List[T], v: T): Option[T] = {
	  if (list.isEmpty) None
	  else {
	    if ( list.head == v ) Some(list.head)
		else content[T](list.tail, v)
	  }
	}
	content[Proposition](list, p)
  }
  
  def get: List[Proposition] = list
}
