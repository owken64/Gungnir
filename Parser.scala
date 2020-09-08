package com.yamanogusha.parser

import scala.util.parsing.combinator._
import java.io.FileReader

import com.yamanogusha.base._

class MyParser extends RegexParsers {
  val name: Parser[String] = """[a-zA-Z]""".r
  val term: Parser[String] = """[a-zA-Z]+""".r
  val ident_var: Parser[Char] = "var"~>name ^^ { name => name.head }
  val ident_eq: Parser[(List[Char], List[Char])] = "equation"~>term~"="~term ^^ { case lhs~"="~rhs => (lhs.toList, rhs.toList) }
  val expr: Parser[(List[Char], List[(List[Char], List[Char])])] = rep(ident_var)~rep(ident_eq) ^^ { case vars~eqs => (vars, eqs) } 
}

class RuntimeParser extends RegexParsers {
  val name: Parser[String] = """[a-zA-Z]""".r
  val number: Parser[String] = """[+-]?[0-9]+(.[0-9]+)?""".r
  val expr: Parser[(Char, Double)] = name~"="~number ^^ { case name~"="~value => (name.head, value.toDouble) }
}

object Launcher {
  def main(args: Array[String]) {
    val reader = new FileReader(args(0))
	val parser = new MyParser()
	val result = parser.parseAll(parser.expr, reader)
	reader.close()
    val data = result.get
	val vs_char = data._1
	val es_char = data._2
	for( c <- vs_char){ new Var(c) }
	for( t <- es_char ) { Equation( t._1.map(VarManager.get(_)), t._2.map(VarManager.get(_)) ) }
    VarManager.list
    EqManager.list
	
	val rp: RuntimeParser = new RuntimeParser
	var msg: String = scala.io.StdIn.readLine()
	while ( msg != "quit" ) {
	  val result = rp.parseAll(rp.expr, msg)
	  if ( result.successful ) {
	    val token: (Char, Double) = result.get
		VarManager.get(token._1).substitute(token._2)
	  }
	  msg = scala.io.StdIn.readLine()
	}
  }
} 
