package com.antyzero.autoinposter


object TestData {

    internal val validMessagesFiles = arrayOf(
            "/message_variant_01.txt",
            "/message_variant_02.txt",
            "/message_variant_03.txt")

    internal val validMessages = validMessagesFiles.map { it to resourceText(it) }
}