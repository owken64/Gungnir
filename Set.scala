package com.yamanogusha.scale.base

sealed class Set {
  def contains( e: Element ): Boolean = {
    if ( e.parent == this ) true
	else false
  }
}

object EmptySet extends Set {
  override def contains(e: Element): Boolean = false
}

object N extends Set
object Z extends Set
object Q extends Set
object R extends Set
object C extends Set

sealed class Element(val parent: Set)