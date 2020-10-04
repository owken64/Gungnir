package com.yamanogusha.gungnir.base

abstract class Object(val classname: String)

object WordManager {
  private var list: List[String] = List()
  def add(word: String): Unit = {
    if (list contains word) {
	  throw new Exception( word + " has been already defined.")
	}
	else {
	  list = word :: list
	}
  }
  
  def get: List[String] = list
}
