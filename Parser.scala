package com.yamanogusha.scale.parser

import scala.util.parsing.combinator._

class MyParser extends RegexParsers {
  val name: Parser[String] = """[a-zA-Z]""".r
  val term: Parser[String] = """[a-zA-Z]+""".r
  val ident_var: Parser[Char] = "var"~>name ^^ { name => name.head }
  val ident_set: Parser[Char] = "set"~>name ^^ { name => name.head }
  val ident_eq: Parser[(List[Char], List[Char])] = "equation"~>term~"="~term ^^ { case lhs~"="~rhs => (lhs.toList, rhs.toList) }
  val ident_ineq: Parser[(List[Char], List[Char])] = "inequality"~>term~(">"|">="|"<"|"<=")~term ^^ { case lhs~sign~rhs => (lhs.toList, rhs.toList) }
  val expr: Parser[(List[Char], List[(List[Char], List[Char])])] = rep(ident_var | ident_set)~rep(ident_eq) ^^ { case vars~eqs => (vars, eqs) } 
}

class RuntimeParser extends RegexParsers {
  val name: Parser[String] = """[a-zA-Z]""".r
  val number: Parser[String] = """[+-]?[0-9]+(.[0-9]+)?""".r
  val expr: Parser[(Char, Double)] = name~"="~number ^^ { case name~"="~value => (name.head, value.toDouble) }
}

