package com.yamanogusha.scale

import java.io.FileReader

import com.yamanogusha.scale.base._
import com.yamanogusha.scale.parser._

object Launcher {
  def main(args: Array[String]) {
	val parser = new MyParser()
    def makeObjects(filename: String): Unit = {
	  val reader = new FileReader(filename)
  	  val result = parser.parseAll(parser.expr, reader)
	  reader.close()
      val data = result.get
	  val vs_char = data._1
	  val es_char = data._2
	  for( c <- vs_char){ new Var(c) }
	  for( t <- es_char ) { Equation( t._1.map(VarManager.get(_)), t._2.map(VarManager.get(_)) ) }
	}
	
    makeObjects("src/Scale/Preparation.scale")
	for( arg <- args ) { makeObjects(arg) }

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
