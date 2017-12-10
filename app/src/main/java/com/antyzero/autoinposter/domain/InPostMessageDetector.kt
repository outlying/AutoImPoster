package com.antyzero.autoinposter.domain

class InPostMessageDetector(
        private val linkExtractor: LinkExtractor = LinkExtractor) {

    private val keywords = arrayOf("paczkomat", "bedzie", "konca")

    /**
     * Analise content of message and try to tell if given message is InPost message
     */
    fun isInPostMessage(message: Message): Boolean {

        val weightWords = containKeywords(message.text).times(0.95f)
        val weightNumber = isNumberValid(message.phoneNumber).times(1f)

        return (weightWords + weightNumber).div(2) >= 0.8f
    }

    /**
     * Checks if content contains characteristic words
     */
    internal fun containKeywords(text: String): Float {

        val matchedKeywords = keywords
                .map { text.contains(it, true) }
                .map { if (it) 1 else 0 }
                .sum().toFloat()

        return matchedKeywords.div(keywords.size)
    }

    /**
     * Checks number of sender
     */
    internal fun isNumberValid(number: String) = if (number == "InPost") 1f else 0f

    /**
     * Detection parameter
     */
    data class Message(
            val phoneNumber: String,
            val text: String)
}