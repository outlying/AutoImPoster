package com.antyzero.autoinposter

object InPostMessageDetector {

    private val keywords = arrayOf("paczkomat", "bedzie", "konca", "dnia", "kliknij")

    /**
     * Analise content of message and try to tell if given message is InPost message
     */
    fun isInPostMessage(message: Message): Boolean {
        return false
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
     * Looks for activation link
     */
    internal fun hasActivationLink(text: String): Float {



        return 0f
    }

    /**
     * Checks number of sender
     */
    internal fun isNumberValid(number: String) = number == "InPost"

    /**
     * Detection parameter
     */
    data class Message(
            val phoneNumber: String,
            val text: String)
}