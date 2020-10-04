package com.yamanogusha.gungnir.parser

import scala.util.parsing.combinator._

import com.yamanogusha.gungnir.base._

class PreparationParser extends RegexParsers {
  val word: Parser[String] = """\S+""".r
  val define: Parser[String] = "define"~>word
  val expr: Parser[List[String]] = rep( define )
}

class RuntimeParser extends RegexParsers {
  def word: Parser[Any] = {
    def combine(list: List[String]): Parser[String] = {
	  if ( list.isEmpty ) failure("not accepted.")
	  else list.head | combine(list.tail)
	}
    combine( WordManager.get )
  }
  def expr: Parser[Any] = rep(word) 
}

class BonusParser extends RegexParsers {
  val expr: Parser[Any] = "throw"~"Gungnir"
}
