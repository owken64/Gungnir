package com.isageek.yamanogusha.gungnir.parser

import scala.util.parsing.combinator._

import com.isageek.yamanogusha.gungnir.base._

class PropositionParser extends RegexParsers {
  val proposition: Parser[Proposition] = """[^:]+""".r ^^{case sentence => Proposition(sentence) }
  val truth: Parser[Boolean] = "True" ^^{ case _ => true }| "False" ^^ { case _ =>false}
  val sentence: Parser[Proposition] = proposition ~ ":" ~ truth ^^{case p ~ ":" ~ tf => { p.truth = Some(tf); p } }| 
                                      proposition 
  val expr: Parser[List[Proposition]] = rep( sentence )
}

class RuntimeParser extends RegexParsers {
  def proposition: Parser[Proposition] = {
    def combine(list: List[Proposition]): Parser[Proposition] = {
	  if ( list.isEmpty ) failure("not accepted.")
	  else list.head.sentence ^^ { case s => Proposition(s) } | combine(list.tail)
	}
    combine( PropositionManager.get )
  }
  def expr: Parser[Proposition] = proposition 
}

class BonusParser extends RegexParsers {
  val expr: Parser[Any] = "throw"~"Gungnir"
}
