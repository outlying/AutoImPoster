package com.antyzero.autoinposter


fun resourceText(file: String) = String::class.java.getResource(file).readText()

fun Array<String>.readTexts() = map { resourceText(it) }