package com.yamanogusha.gungnir.base

abstract class Object(val classname: String)

class Var(val name: Char) {
  VarManager.add(this)
  var value: Option[Double] = None
  def substitute(v: Double): Unit = {
    val previousValue: Option[Double] = value
	value = Some(v)
	try {
      EqManager.check()
	}
	catch {
  	  case ex:Exception => {
	    value = previousValue
		println(ex)
	  }
	}
  }
  
  override def toString(): String = {
    if ( value.isDefined ) {
	  name + " = " + value.get
	}
	else {
	  name + " : " + "unknown"
	}
  }
}

case class Equation(lhs: List[Var], rhs: List[Var]) {
  EqManager.add(this)
  def check(): Unit = {
    if ( lhs.forall( v => v.value.isDefined ) && rhs.forall( v => v.value.isDefined ) ) {
      val lhs_value: List[Double] = for( v <- lhs ) yield { v.value.get }
	  val rhs_value: List[Double] = for( v <- rhs ) yield { v.value.get }
	  if ( lhs_value.reduceLeft( _ * _ ) != rhs_value.reduceLeft( _ * _ ) ) {
	    throw new Exception("Equation " + this + " is collapsed.")
      }
	}
  }
  
  override def toString(): String = {
    def combine(list: List[Var]): String = {
	  if ( list == Nil ) ""
	  else list.head.name.toString + combine(list.tail)
	}
	combine(lhs) + " = " + combine(rhs)
  }
}

case class Inequality(lhs: List[Var], rhs: List[Var])

object NameManager {
  private var nameSet: scala.collection.immutable.Set[Char] = scala.collection.immutable.Set()
  def add(c: Char): Unit = {
    if ( !nameSet.contains(c) ) {
	  nameSet = nameSet + c
	}
	else {
	  throw new Exception(c + " has been already used.")
	}
  }
  
  def contains(c: Char): Boolean = nameSet.contains(c)
}

object VarManager {
  var varMap: scala.collection.immutable.Map[Char, Var] = scala.collection.immutable.Map()
  def add(v: Var): Unit = {
    if ( !varMap.contains(v.name) ) {
      varMap = varMap + (v.name -> v )
	}
	else {
	  throw new Exception("Variable " + varMap(v.name) + " has been already defined.")
	}
  }
  def get(k: Char): Var = { varMap(k) }
  def list(): Unit = {
    for ( v <- varMap.values ) { println(v) }
  }
}

object EqManager {
  var eqList: List[Equation] = List()
  def add(e: Equation): Unit = {
    if ( ! eqList.contains(e) ) {
	  eqList = e :: eqList
	}
	else {
	  throw new Exception("Equation" + e + " has been already defined.")
	}
  }
  
  def check(): Unit = {
    for ( e <- eqList ) { e.check() }
  }

  def list(): Unit = {
    for ( e <- eqList ) { println(e) }
  }
}
  