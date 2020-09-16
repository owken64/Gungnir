package com.yamanogusha.gungnir.base

abstract sealed class Number(classname: String) extends Object(classname)

class Integer(val value: Int) extends Number("Integer")
