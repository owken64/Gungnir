package com.yamanogusha.gungnir.base

sealed abstract class Set extends Object("Set") {
  def contains(obj: Object): Boolean
}

class finiteSet(list: List[Object]) {
  val value = scala.collection.immutable.Set[Object](list)
  def contains(obj: Object): Boolean = { value.contains(obj) }
}

class SetWithRequires(val requires: List[Object => Boolean] ) extends Set {
  def contains(obj: Object): Boolean = { requires.forall( P => (P(obj) == true) ) }
}
