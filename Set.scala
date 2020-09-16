package com.yamanogusha.gungnir.base

sealed abstract class Set extends Object("Set") {
  def contains(obj: Object): Boolean
}

class SetWithRequires(val requires: List[Object => Boolean] ) extends Set {
  def contains(obj: Object): Boolean = { requires.forall( P => (P(obj) == true) ) }
}
