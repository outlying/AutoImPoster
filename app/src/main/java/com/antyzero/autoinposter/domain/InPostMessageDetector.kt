package com.antyzero.autoinposter.domain

class InPostMessageDetector(
        private val linkExtractor: LinkExtractor = LinkExtractor,
        private val phoneNumberMatcher: (String) -> Boolean = DEFAULT_NUMBER_MATCHER) {

    private val keywords = arrayOf("paczkomat", "bedzie", "konca")

    /**
     * Analise content of message and try to tell if given message is InPost message
     */
    fun isInPostMessage(message: Message, accuracy: Float = DEFAULT_ACCURACY): Boolean {

        assert(accuracy in 0f..1f) {
            "Accuracy value has to be in range of [0,1]"
        }

        val activationLinkValue = if (linkExtractor.hasActivationLink(message.text)) 1f else 0f

        val input = arrayOf(
                2f to containKeywords(message.text),
                4f to isNumberValid(message.phoneNumber),
                4f to activationLinkValue)

        val top = input.map { it.first * it.second }.sum()
        val bottom = input.map { it.first }.sum()
        val result = top / bottom

        return result >= accuracy
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
    internal fun isNumberValid(number: String) = if (phoneNumberMatcher.invoke(number)) 1f else 0f

    /**
     * Detection parameter
     */
    data class Message(
            val phoneNumber: String,
            val text: String)

    companion object {

        private const val DEFAULT_ACCURACY = 0.8f

        private val DEFAULT_NUMBER_MATCHER: (String) -> Boolean = { it == "InPost" }
    }
}