package com.antyzero.autoinposter.domain


object TestMessages {

    val validMessages: List<TestMessage> by lazy {

        arrayOf(
                TestMessage("/message_variant_01.txt", "n5d7of"),
                TestMessage("/message_variant_02.txt", "kg1vc0"),
                TestMessage("/message_variant_03.txt", "bg9vy6"))
                .map {
                    it.message = resourceText(it.file)
                    it
                }
    }

    fun resourceText(file: String): String {
        return (javaClass::getResource)(file).readText()
    }

    data class TestMessage(
            val file: String,
            val linkId: String?) {

        var message: String = ""
    }
}