package com.antyzero.autoinposter.domain


fun resourceText(file: String) = String::class.java.getResource(file).readText()

fun Array<String>.readTexts() = map { resourceText(it) }