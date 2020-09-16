package com.yamanogusha.gungnir.parser

import scala.util.parsing.combinator._

import com.yamanogusha.gungnir.base._

class MyParser extends RegexParsers {
  val name: Parser[String] = """[a-zA-Z]""".r
  val term: Parser[String] = """[a-zA-Z]+""".r
  val ident_var: Parser[Char] = "var"~>name ^^ { name => name.head }
  val ident_eq: Parser[(List[Char], List[Char])] = "equation"~>term~"="~term ^^ { case lhs~"="~rhs => (lhs.toList, rhs.toList) }
  val ident_ineq: Parser[(List[Char], List[Char])] = "inequality"~>term~(">"|">="|"<"|"<=")~term ^^ { case lhs~sign~rhs => (lhs.toList, rhs.toList) }
  val expr: Parser[(List[Char], List[(List[Char], List[Char])])] = rep(ident_var)~rep(ident_eq) ^^ { case vars~eqs => (vars, eqs) } 
}

class RuntimeParser extends RegexParsers {
  val name: Parser[String] = """[a-zA-Z]""".r
  val number: Parser[String] = """[+-]?[0-9]+(.[0-9]+)?""".r
  val expr: Parser[(Char, Double)] = name~"="~number ^^ { case name~"="~value => (name.head, value.toDouble) }
}

class PreparationParser extends RegexParsers {
  val name: Parser[String] = "[a-zA-Z]".r
  val classname: Parser[String] = "[a-zA-Z]+".r
  val integer: Parser[Integer] = "[+-]?[1-9][0-9]*".r ^^ { str => new Integer( str.toInt ) }
  val set: Parser[Set] = "{"~name~"|"~name~"is"~classname~"}" ^^ 
            { case "{"~elem~"|"~elem2~"is"~classname~"}" if elem == elem2 => new SetWithRequires(List( ((obj: Object) => (obj.classname == classname) ) ) ) }
  val expr: Parser[Object] = integer | set
}

class BonusParser extends RegexParsers {
  val expr: Parser[Any] = "throw"~"Gungnir"
}
