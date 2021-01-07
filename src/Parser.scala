package com.is_a_geek.yamanogusha.ronri.parser

import scala.util.parsing.combinator._

import com.is_a_geek.yamanogusha.ronri.base._

class PropositionParser extends RegexParsers {
  val proposition: Parser[Proposition] = """[^:;]+""".r ^^{case sentence => Proposition(sentence) }
  val truth: Parser[Boolean] = "True" ^^{ case _ => true }| "False" ^^ { case _ =>false}
  val sentence: Parser[Proposition] = proposition ~ ":" ~ truth ^^{case p ~ ":" ~ tf => { p.t = Some(tf); p } }| 
                                      proposition
  val expr: Parser[List[Proposition]] = rep( sentence <~";" )
}

class RuntimeParser extends RegexParsers {
  def proposition: Parser[Proposition] = {
    def combine(list: List[Proposition]): Parser[Proposition] = {
	  if ( list.isEmpty ) failure("not accepted.")
	  else list.head.sentence ^^ { case s => PropositionManager.get(Proposition(s)).get } | combine(list.tail)
	}
    combine( PropositionManager.get )
  }
  def t: Parser[LogicalFormula] = ("true" | "True") ^^{ _ => True() }
  def f: Parser[LogicalFormula] = ("false" | "False") ^^{ _ => False() }
  def negation: Parser[LogicalFormula] = "not"~formula ^^ { case "not"~f => Not(f) }
//  def conjunction: Parser[LogicalFormula] = formula~"and"~formula ^^ { case f1~"and"~f2 => And(f1, f2) }
//  def disjunction: Parser[LogicalFormula] = formula~"or"~formula ^^ { case f1~"or"~f2 => Or(f1, f2) }
  def implication: Parser[LogicalFormula] = "If"~formula~", then"~formula ^^ { case "If"~f1~", then"~f2 => IfThen(f1, f2) } 
//  def equivalence: Parser[LogicalFormula] = formula~"if and only if"~formula ^^ { case f1~"if and only if"~f2 => Iff(f1, f2) }
  def conjunction: Parser[LogicalFormula] = "and"~formula~formula ^^ { case "and"~f1~f2 => And(f1, f2) }
  def disjunction: Parser[LogicalFormula] = "or"~formula~formula ^^ { case "or"~f1~f2 => Or(f1, f2) }
  def equivalence: Parser[LogicalFormula] = "iff"~formula~formula ^^ { case "iff"~f1~f2 => Iff(f1, f2) }
									       
  def formula:Parser[LogicalFormula] =  proposition | t | f | negation | conjunction | disjunction | implication | equivalence
   
  def expr: Parser[LogicalFormula] = formula
}
