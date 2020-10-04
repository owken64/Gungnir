package com.yamanogusha.gungnir

import java.io.FileReader

import com.yamanogusha.gungnir.base._
import com.yamanogusha.gungnir.parser._

object Launcher {
  def main(args: Array[String]) {
	val parser = new PreparationParser()
    def makeObjects(filename: String): Unit = {
	  val reader = new FileReader(filename)
  	  val result = parser.parseAll(parser.expr, reader)
	  reader.close()
      val data = result.get
	  for (word <- data) { WordManager.add(word) }
	}
	
	for( arg <- args ) { makeObjects(arg) }
	
	val rp: RuntimeParser = new RuntimeParser
	var msg: String = scala.io.StdIn.readLine()
	while ( msg != "quit" ) {
	  val result = rp.parseAll(rp.expr, msg)
	  if ( result.successful ) {
	    println("Accepted.") 
	  }
	  else {
	    println("Rejected.")
	  }
	  msg = scala.io.StdIn.readLine()
	}
  }
}
