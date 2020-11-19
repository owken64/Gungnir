package com.isageek.yamanogusha.gungnir.base

case class Proposition(val sentence:String){
  var truth: Option[Boolean] = None
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
