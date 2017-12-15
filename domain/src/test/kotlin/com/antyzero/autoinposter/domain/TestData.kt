package com.antyzero.autoinposter.domain


object TestData {

    internal val validMessages = arrayOf(
            TestMessage("/message_variant_01.txt", "n5d7of"),
            TestMessage("/message_variant_02.txt", "kg1vc0"),
            TestMessage("/message_variant_03.txt", "bg9vy6"))
}

data class TestMessage(
        val file: String, val linkId: String?) {

    val message = resourceText(file)
}