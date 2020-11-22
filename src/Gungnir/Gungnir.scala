package com.isageek.yamanogusha.gungnir

import java.io.FileReader

import com.isageek.yamanogusha.gungnir.base._
import com.isageek.yamanogusha.gungnir.parser._

object Launcher {
  def main(args: Array[String]) {
	val parser = new PropositionParser()
    def makeObjects(filename: String): Unit = {
      val reader = new FileReader(filename)
      val result = parser.parseAll(parser.expr, reader)
	  reader.close()
      val data = result.get
	  for (proposition <- data) { PropositionManager.add(proposition) }
	}
		
//	for( arg <- args ) { makeObjects(arg) }
    makeObjects("src/Gungnir/Proposition.gung")

	println(PropositionManager.get )

    print(">")
	val rp: RuntimeParser = new RuntimeParser
	var msg: String = scala.io.StdIn.readLine()
	while ( msg != "quit" ) {
	  val result = rp.parseAll(rp.expr, msg)
	  if ( result.successful ) {
	    val p: LogicalFormula = result.get
//	    println("Accepted.") 
        if (p.truth.isDefined) {
          println(p.truth.get)
	    }
		else {
		  println("Unknown")
		}
	  }
	  else {
	    println("Rejected.(This sentence is not proposition or you haven't defined this.)")
	  }
	  print(">")
	  msg = scala.io.StdIn.readLine()
	}
  }
}
